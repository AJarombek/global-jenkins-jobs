/**
 * Jenkins script for pushing a Jenkins Docker image to an ECR repository.
 * @author Andrew Jarombek
 * @since 6/10/2020
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
            description: 'Label/Version of the Docker image to push to an ECR repository'
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
        stage("Cleanup Docker Environment") {
            steps {
                script {
                    cleanupDockerEnvironment()
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
        git.basicClone('global-aws-infrastructure', 'main')
    }
}

def pushImage() {
    def repoUrl = "739088120071.dkr.ecr.us-east-1.amazonaws.com"

    sh "aws ecr get-login-password --region us-east-1 | sudo docker login -u AWS --password-stdin $repoUrl"

    dir('repos/global-aws-infrastructure/jenkins-kubernetes/docker') {
        def status = sh (
            script: """
                set +e
                set -x
                export AWS_DEFAULT_REGION=us-east-1
                ./push-ecr.sh ${params.label} 
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

def cleanupDockerEnvironment() {
    def repoUrl = "739088120071.dkr.ecr.us-east-1.amazonaws.com"
    def imageName = "jenkins-jarombek-io"

    sh """
        sudo docker image rm $imageName:latest
        sudo docker image rm $repoUrl/$imageName:$params.label
        
        sudo docker image ls
    """
}

def postScript() {
    email.sendEmail(
        "Push Jenkins Docker Image to ECR",
        "",
        env.JOB_NAME,
        currentBuild.result,
        env.BUILD_NUMBER,
        env.BUILD_URL
    )

    cleanWs()
}