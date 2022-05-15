/**
 * Jenkins script for detecting unexpected AWS costs.
 * @author Andrew Jarombek
 * @since 7/4/2020
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
        stage("Detect AWS Costs") {
            steps {
                script {
                    detectAWSCosts()
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
    dir('repos/global-aws-infrastructure') {
        git.basicClone('global-aws-infrastructure', 'master')
    }
}

def setupEnvironment() {
    infrastructuresteps.setupEnvironment('repos/global-aws-infrastructure/scripts')
}

def detectAWSCosts() {
    dir('repos/global-aws-infrastructure/scripts') {
        String cost_string = sh (
            script: "pipenv run python costDetection.py",
            returnStdout: true
        )

        println cost_string
        float cost = cost_string as float

        if (cost <= 8.5) {
            currentBuild.result = "SUCCESS"
        } else if (cost > 8.5 && cost <= 9.5) {
            currentBuild.result = "UNSTABLE"
        } else {
            currentBuild.result = "FAILURE"
        }

        env.AVG_COST = cost_string
    }
}

def postScript() {
    def bodyTitle = "Detect AWS Costs"
    def bodyContent = "3-Day Cost Average: $env.AVG_COST"
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}
