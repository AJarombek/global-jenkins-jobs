/**
 * Jenkins script for testing the SaintsXCTF AWS infrastructure.
 * @author Andrew Jarombek
 * @since 3/24/2019
 */

@Library(['global-jenkins-library@master']) _

final String PARAM_BRANCH = env.branch

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
                    git.basicClone('saints-xctf-web', PARAM_BRANCH)
                }
            }
        }
        stage("Execute Unit Tests") {
            steps {
                script {
                    dir("test") {
                        // pipenv install
                        def status = sh (
                            script: "#!/bin/bash \n" +
                                    """
                                set +e
                                set -x
                                pipenv install
                                pipenv shell
                                
                                exit_status=\$?
    
                                cat test_results.log
                                exit \$exit_status
                            """,
                            returnStatus: true
                        )
                    }
                }
            }
        }
    }
    post {
        always {
            script {
                cleanWs()
            }
        }
    }
}