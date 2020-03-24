/**
 * Jenkins script for testing saints-xctf-web.
 * @author Andrew Jarombek
 * @since 3/14/2020
 */

// 15 West 64th Street, Begin setting up furniture, Too many IKEA trips

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
            logRotator(daysToKeep: '10', numToKeepStr: '5')
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
        stage("Setup Project") {
            steps {
                script {
                    sh '''
                        set -x
                        node --version
                        npm --version
                        
                        npm install yarn -g
                        yarn
                    '''
                }
            }
        }
        stage("Execute Tests") {
            steps {
                script {
                    try {
                        def status = sh (
                            script: """
                                set +e
                                set -x
                                yarn run client:test >> test_results.log
                                exit_status=\$?
    
                                cat test_results.log
    
                                exit \$exit_status
                            """,
                            returnStatus: true
                        )

                        if (status >= 1) {
                            currentBuild.result = "UNSTABLE"
                        }

                    } catch (Exception ex) {
                        echo "SaintsXCTF Web Application Testing Failed"
                        currentBuild.result = "UNSTABLE"
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

                def bodyTitle = "SaintsXCTF Web Application Tests"
                email.sendEmail(
                    bodyTitle,
                    bodyContent,
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