/**
 * Jenkins script for pushing a Jenkins Docker image to an ECR repository.
 * @author Andrew Jarombek
 * @since 6/10/2020
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        label: 'master'
    }
    options {
        ansiColor('xterm')
        timeout(time: 1, unit: 'HOURS')
        buildDiscarder(
            logRotator(daysToKeepStr: 10, numToKeepStr: 5)
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
                    dir('repos/saints-xctf-infrastructure') {
                        git.basicClone('saints-xctf-infrastructure', 'master')
                    }
                }
            }
        }
        stage("Push Docker Image") {
            steps {
                script {
                    sh "TODO"
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