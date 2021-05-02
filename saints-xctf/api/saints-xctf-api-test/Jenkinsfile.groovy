/**
 * Jenkins script for testing the SaintsXCTF API unit tests.
 * @author Andrew Jarombek
 * @since 3/27/2020
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        kubernetes {
            yaml """\
                apiVersion: v1
                kind: Pod
                metadata:
                  name: saints-xctf-api-test
                  namespace: jenkins
                  labels:
                    version: v1.0.0
                    environment: development
                    application: saints-xctf-api-test
                spec:
                  containers:
                    - name: auth
                      image: ajarombek/mock-saints-xctf-auth:latest
                      tty: true
                    - name: functions
                      image: ajarombek/mock-saints-xctf-functions:latest
                      tty: true
                    - name: database
                      image: mysql:5.7
                      env:
                        - name: MYSQL_ROOT_PASSWORD
                          value: saintsxctftest
                        - name: MYSQL_DATABASE
                          value: saintsxctf
                        - name: MYSQL_USER
                          value: test
                        - name: MYSQL_PASSWORD
                          value: test
                      tty: true
                    - name: mysql-aws
                      image: ajarombek/mysql-aws:latest
                      tty: true
                    - name: test
                      image: python:3.8
                      tty: true
            """.stripIndent()
        }
    }
    triggers {
        cron('H 0 * * *')
    }
    options {
        ansiColor('xterm')
        timeout(time: 1, unit: 'HOURS')
        buildDiscarder(logRotator(daysToKeepStr: '10', numToKeepStr: '5'))
    }
    stages {
        stage("Clean Workspace") { steps { script { cleanWs() } } }
        stage("Checkout Repository") { steps { script { checkoutRepo() } } }
        stage("Setup Database") { steps { script { setupDatabase() } } }
        stage("Setup API") { steps { script { setupAPI() } } }
        stage("Execute Tests") { steps { script { executeTests() } } }
    }
    post {
        always {
            script { postScript() }
        }
    }
}

def checkoutRepo() {
    container('test') {
        genericsteps.checkoutRepo('saints-xctf-api', 'master')
    }
}

def setupDatabase() {
    container('mysql-aws') {
        sh '''
            aws --version
            export AWS_DEFAULT_REGION=us-east-1
            aws s3 cp s3://saints-xctf-db-backups-prod/backup.sql backup.sql
        '''
    }

    container('database') {
        sh '''
            mysql -u test --password=test saintsxctf < backup.sql
        '''
    }
}

def setupAPI() {
    container('test') {
        sh 'apt-get update'

        dir('repos/saints-xctf-api/api/src') {
            sh '''
                pip install pipenv
                pipenv install
            '''
        }
    }
}

def executeTests() {
    container('test') {
        dir('repos/saints-xctf-api/api/src') {
            genericsteps.shReturnStatus(
                """
                    set +e
                    set -x
                    export ENV=test
                    export AWS_DEFAULT_REGION=us-east-1
                    
                    # See all the endpoints exposed by Flask, ensure there are no syntax errors in the Python files.
                    pipenv run flask routes
                                    
                    pipenv run flask test
                """
            )
        }
    }
}

def postScript() {
    def bodyContent = ""
    def bodyTitle = "SaintsXCTF API Tests"
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}