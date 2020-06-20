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
                    dir('repos/global-aws-infrastructure') {
                        git.basicClone('global-aws-infrastructure', 'master')
                    }
                }
            }
        }
        stage("Push Docker Image") {
            steps {
                script {
                    dir('repos/global-aws-infrastructure/jenkins-kubernetes/docker') {
                        def status = sh (
                            script: """
                                set +e
                                set -x
                                export AWS_DEFAULT_REGION=us-east-1
                                ./push-ecr.sh $params.string 
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
            }
        }
    }
    post {
        always {
            script {
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
        }
    }
}