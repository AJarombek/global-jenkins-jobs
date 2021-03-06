/**
 * Jenkins script for testing AWS infrastructure in jarombek-com-infrastructure.
 * @author Andrew Jarombek
 * @since 6/2/2019
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        label 'master'
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
    dir('repos/jarombek-com-infrastructure') {
        git.basicClone('jarombek-com-infrastructure', branch)
    }
}

def setupEnvironment() {
    infrastructuresteps.setupEnvironment('repos/jarombek-com-infrastructure/test')
}

def executeTests() {
    infrastructuresteps.executeTests('repos/jarombek-com-infrastructure/test', getTestEnv())
}

def postScript() {
    def directory = 'repos/jarombek-com-infrastructure/test'
    def bodyTitle = "jarombek.com AWS Infrastructure Tests"
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    infrastructuresteps.postScript(directory, bodyTitle, jobName, buildStatus, buildNumber, buildUrl)
}

@NonCPS
def getTestEnv() {
    def matches = JOB_NAME =~ /jarombek-com-aws-infrastructure-test-(\w+)/
    return matches[0][1]
}