/**
 * Jenkins script for testing jarombek-com-infrastructure.
 * @author Andrew Jarombek
 * @since 6/2/2019
 */

final String PARAM_BRANCH = branch

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
        stage("Checkout") {
            steps {
                script {
                    checkout([$class: 'GitSCM',
                              branches: [[name: "*/$PARAM_BRANCH"]],
                              credentialsId: "ajarombek-github",
                              userRemoteConfigs: [[url: "git@github.com:AJarombek/jarombek-com-infrastructure.git"]]])
                }
            }
        }
        stage("Execute Tests") {
            steps {
                script {
                    dir("test") {
                        try {
                            def status = sh (
                                script: "#!/bin/bash \n" +
                                """
                                    set +e
                                    set -x
                                    python3.8 --version
                                    python3.8 -m pip --version
        
                                    python3.8 -m venv env
                                    source ./env/bin/activate
                                    python3.8 -m pip install -r requirements.txt
        
                                    # The AWS SDK needs to know which region the infrastructure is in.
                                    export AWS_DEFAULT_REGION=us-east-1
                                    
                                    export TEST_ENV=$env
                                    python3.8 runner.py test_results.log
                                    exit_status=\$?
        
                                    cat test_results.log
        
                                    deactivate
                                    exit \$exit_status
                                """,
                                returnStatus: true
                            )

                            if (status >= 1) {
                                currentBuild.result = "UNSTABLE"
                            }

                        } catch (Exception ex) {
                            echo "Infrastructure Testing Failed"
                            currentBuild.result = "UNSTABLE"
                        }
                    }
                }
            }
        }
    }
    post {
        always {
            script {
                def bodyContent = ""
                def testResultLog = ""

                dir("test") {
                    testResultLog = readFile "test_results.log"
                }

                testResultLog.split('\n').each {
                    bodyContent += "<p style=\"font-family: Consolas,monaco,monospace\">$it</p>"
                }

                def bodyTitle = "JarombekCom Infrastructure ${get_env().toUpperCase()} Tests"
                email.sendEmail(bodyTitle, bodyContent, env.JOB_NAME, currentBuild.result, env.BUILD_NUMBER, env.BUILD_URL)

                cleanWs()
            }
        }
    }
}