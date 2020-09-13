/**
 * Jenkins script for testing the saints-xctf-auth API.
 * @author Andrew Jarombek
 * @since 9/13/2020
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        label 'master'
    }
    parameters {
        choice(
            name: 'environment',
            choices: ['prod', 'dev'],
            description: 'Environment to create the database backup/restore functions.'
        )
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
                    checkoutRepo()
                }
            }
        }
        stage("Execute Tests") {
            steps {
                script {
                    executeTestScript()
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
    def name = "saints-xctf-auth"
    def branch = "master"

    genericsteps.checkoutRepo(name, branch)
}

def executeTestScript() {
    test_result = 0
    dir('repos/saints-xctf-infrastructure/test') {
        try {
            withCredentials([
                usernamePassword(
                    credentialsId: 'saintsxctf-andy',
                    passwordVariable: 'password',
                    usernameVariable: 'username'
                )
            ]) {
                test_result = sh(
                    script: """#!/bin/bash
                        export TEST_ENV=$params.environment
                        export CLIENT_SECRET=$password
                        npm test 2>&1 | tee test_results.log
                        exit \${PIPESTATUS[0]}
                    """,
                    returnStatus: true
                )
            }
        } catch (Exception ex) {
            echo "Tests Failed"
            test_result = 1
        }
    }

    if (test_result > 0) {
        currentBuild.result = "UNSTABLE"
    } else {
        currentBuild.result = "SUCCESS"
    }
}

def postScript() {
    def bodyContent = ""
    def testResultLog = ""

    dir('repos/saints-xctf-infrastructure/test') {
        testResultLog = sh(
            script: "cat /src/test_results.log",
            returnStdout: true
        )
    }

    testResultLog.split('\n').each {
        bodyContent += "<p style=\"font-family: Consolas,monaco,monospace\">$it</p>"
    }

    def bodyTitle = "SaintsXCTF Auth Tests"
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}
