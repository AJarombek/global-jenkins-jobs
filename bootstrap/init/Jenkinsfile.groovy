/**
 * Jenkins script for initializing the Jenkins server.  Run this pipeline when the server first boots up.
 * @author Andrew Jarombek
 * @since 6/20/2020
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
        stage("Init Scripts") {
            steps {
                script {
                    sh """
                        # Install additional dependencies not handled by the Dockerfile
                        
                        # 1) Bazel
                        curl https://bazel.build/bazel-release.pub.gpg | sudo apt-key add -
                        echo "deb [arch=amd64] https://storage.googleapis.com/bazel-apt stable jdk1.8" | sudo tee /etc/apt/sources.list.d/bazel.list
                        sudo apt-get update
                        sudo apt-get install bazel
                        
                        # Installed Libraries
                        docker --version
                        python --version
                        node --version
                        aws --version
                        bazel --version
                    """
                }
            }
        }
        stage("Create Jobs") {
            steps {
                script {
                    // The first time JOB DSL scripts are built, they will fail and need approval.
                    build(
                        job: 'seed-job',
                        parameters: [
                            string(name: 'repository', value: 'global-jenkins-jobs'),
                            string(name: 'branch', value: 'master')
                        ],
                        propagate: false
                    )

                    // Pause the job until the user approves the scripts.
                    timeout(time: 1, unit: 'HOURS') {
                        input message: 'Approve Scripts before continuing...', ok: 'Scripts Approved'
                    }

                    // On the Job DSL scripts second run, they should pass.
                    build(
                        job: 'seed-job',
                        parameters: [
                            string(name: 'repository', value: 'global-jenkins-jobs'),
                            string(name: 'branch', value: 'master')
                        ]
                    )
                }
            }
        }
    }
    post {
        always {
            script {
                email.sendEmail(
                    "Jenkins Server Initialized",
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