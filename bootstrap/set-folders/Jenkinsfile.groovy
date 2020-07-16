/**
 * Jenkins script for initializing or resetting the folder structure on the Jenkins server.
 * @author Andrew Jarombek
 * @since 7/15/2020
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
        stage("Reset Folders") {
            steps {
                script {
                    build job: 'single-seed-job', parameters: [
                        string(name: 'repository', value: 'global-jenkins-jobs'),
                        string(name: 'branch', value: 'master'),
                        string(name: 'job_dsl_path', value: 'folders/dsl.groovy')
                    ]
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