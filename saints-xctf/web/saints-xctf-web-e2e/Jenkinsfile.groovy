/**
 * Jenkins script for end to end testing saints-xctf-web.
 * @author Andrew Jarombek
 * @since 5/2/2021
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        kubernetes {
            yaml """\
                apiVersion: v1
                kind: Pod
                metadata:
                  name: saints-xctf-web-e2e
                  namespace: jenkins
                  labels:
                    version: v1.0.0
                    environment: all
                    application: saints-xctf-web-e2e
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
                    - name: api
                      image: python:3.8
                      tty: true
                    - name: web
                      image: node:14.4.0
                      tty: true
                    - name: cypress
                      image: cypress/included:7.2.0
                      tty: true
                  serviceAccountName: jenkins-kubernetes-test
                  automountServiceAccountToken: true
                  restartPolicy: Never
            """.stripIndent()
        }
    }
    triggers {
        cron('H 0 * * *')
    }
    options {
        ansiColor('xterm')
        timeout(time: 1, unit: 'HOURS')
        buildDiscarder(
            logRotator(daysToKeepStr: '10', numToKeepStr: '5')
        )
    }
    stages {
        stage("Clean Workspace") { steps { script { cleanWs() } } }
        stage("Checkout Repositories") { steps { script { checkoutRepos() } } }
    }
    post {
        always {
            script {
                postScript()
            }
        }
    }
}

def checkoutRepos() {
    genericsteps.checkoutRepo("saints-xctf-api", "master")
    genericsteps.checkoutRepo("saints-xctf-web", "master")
}

def postScript() {
    def bodyTitle = "SaintsXCTF Web E2E Testing"
    def bodyContent = ""
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}

