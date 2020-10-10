/**
 * Jenkins script for testing the SaintsXCTF API unit tests.
 * @author Andrew Jarombek
 * @since 3/27/2020
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        kubernetes {
            yamlFile 'saints-xctf/api/saints-xctf-api-test/pod.yaml'
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
        stage("Clean Workspace") {
            steps {
                script {
                    cleanWs()
                }
            }
        }
        stage("Checkout Repository") {
            steps {
                script {
                    checkoutRepo()
                }
            }
        }
        stage("Setup Database") {
            steps {
                script {
                    setupDatabase()
                }
            }
        }
        stage("Setup API") {
            steps {
                script {
                    setupAPI()
                }
            }
        }
        stage("Execute Tests") {
            steps {
                script {
                    executeTests()
                }
            }
        }
    }
    post {
        always {
            script {
                postScript()
            }
        }
    }
}

def checkoutRepo() {
    container('test') {
        genericsteps.checkoutRepo('saints-xctf-api', 'master')
    }
}

def setupDatabase() {
    sh '''
        set +e
        set -x
        sudo pip3 install pipenv
        pipenv --rm
        pipenv install
    '''
}

def setupAPI() {
    container('test') {
        sh 'apt-get update'

        dir('repos/saints-xctf-api/api/src') {
            sh '''
                pip install pipenv
                pipenv install
                export ENV=test
            '''
        }
    }
}

def executeTests() {
    try {
        def status = sh (
            script: """
                set +e
                set -x
                
                # See all the endpoints exposed by Flask, ensure there are no syntax errors in the Python files.
                pipenv run flask routes
                                
                pipenv run flask test
            """,
            returnStatus: true
        )

        if (status >= 1) {
            currentBuild.result = "UNSTABLE"
        }

    } catch (Exception ex) {
        echo "SaintsXCTF API Testing Failed"
        currentBuild.result = "FAILURE"
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