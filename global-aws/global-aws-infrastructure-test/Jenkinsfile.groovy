/**
 * Jenkins script for testing the Global AWS infrastructure.
 * @author Andrew Jarombek
 * @since 3/24/2019
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        label 'master'
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
                    checkoutRepo('master')
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

def checkoutRepo(String branch) {
    dir('repos/global-aws-infrastructure') {
        git.basicClone('global-aws-infrastructure', branch)
    }
}

def setupEnvironment() {
    infrastructuresteps.setupEnvironment('repos/global-aws-infrastructure/test')
}

def executeTests() {
    infrastructuresteps.executeTests('repos/global-aws-infrastructure/test', getTestEnv())
}

def postScript() {
    def directory = 'repos/global-aws-infrastructure/test'
    def bodyTitle = "Global Infrastructure Tests"
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    infrastructuresteps.postScript(directory, bodyTitle, jobName, buildStatus, buildNumber, buildUrl)
}

@NonCPS
def getTestEnv() {
    def matches = JOB_NAME =~ /global-aws-infrastructure-test-(\w+)/
    return matches[0][1]
}