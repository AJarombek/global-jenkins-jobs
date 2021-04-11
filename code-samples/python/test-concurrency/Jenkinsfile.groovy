/**
 * Jenkins script for testing a Python concurrency code snippet.
 * @author Andrew Jarombek
 * @since 10/4/2020
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        label 'master'
    }
    triggers {
        cron('H 0 1,15 * *')
    }
    options {
        ansiColor('xterm')
        timeout(time: 1, unit: 'HOURS')
        timestamps()
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
        stage("Setup Environment") {
            steps {
                script {
                    setupEnvironment()
                }
            }
        }
        stage("Execute Infrastructure Tests") {
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
    genericsteps.checkoutRepo('jarombek-com-sources', 'master')
}

def setupEnvironment() {
    infrastructuresteps.setupEnvironment('repos/jarombek-com-sources/2020/11-Nov/11-01-python-concurrency')
}

def executeTests() {
    dir('repos/jarombek-com-sources/2020/11-Nov/11-01-python-concurrency') {
        try {
            def status = sh (
                script: "pipenv run python test.py",
                returnStatus: true
            )

            if (status >= 1) {
                currentBuild.result = "UNSTABLE"
            } else {
                currentBuild.result = "SUCCESS"
            }
        } catch (Exception ex) {
            echo "Python Concurrency Tests Failed"
            currentBuild.result = "FAILURE"
        }
    }
}

def postScript() {
    def bodyTitle = "Test Python Concurrency Code Samples"
    def bodyContent = ""
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}