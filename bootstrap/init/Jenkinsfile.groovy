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
                        echo 'Install additional dependencies here...'
                        
                        # Installed Libraries
                        docker --version
                        python --version
                        node --version
                        aws --version
                    """
                }
            }
        }
        stage("Init Scripts") {
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
                    input message: 'Approve Scripts before continuing', ok: 'Scripts Approved'

                    // On the scripts second run, it should pass.
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