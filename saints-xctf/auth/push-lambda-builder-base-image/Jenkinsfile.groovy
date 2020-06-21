/**
 * Jenkins script for pushing a Docker image that helps build AWS Lambda functions to DockerHub.
 * @author Andrew Jarombek
 * @since 6/21/2020
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        label 'master'
    }
    parameters {
        string(
            name: 'label',
            defaultValue: '1.0.0',
            description: 'Label/Version of the Docker image to push to DockerHub'
        )
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
        stage("Push Docker Image") {
            steps {
                script {
                    pushImage()
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
    dir('repos/saints-xctf-infrastructure') {
        git.basicClone('saints-xctf-infrastructure', 'master')
    }
}

def pushImage() {
    dir("repos/saints-xctf-infrastructure") {
        def status = sh (
            script: """
                export AWS_DEFAULT_REGION=us-east-1
            """,
            returnStatus: true
        )

        if (status >= 1) {
            currentBuild.result = "UNSTABLE"
        } else {
            currentBuild.result = "SUCCESS"
        }
    }
}

def postScript() {
    email.sendEmail(
        "Push AWS Lambda function builder Docker image to DockerHub.",
        "",
        env.JOB_NAME,
        currentBuild.result,
        env.BUILD_NUMBER,
        env.BUILD_URL
    )

    cleanWs()
}
