/**
 * Jenkins script for testing jarombek-com.
 * @author Andrew Jarombek
 * @since 5/12/2019
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        kubernetes {
            yaml """\
                apiVersion: v1
                kind: Pod
                metadata:
                  name: jarombek-com-test
                  namespace: jenkins
                  labels:
                    version: v1.0.0
                    environment: development
                    application: jarombek-com-test
                spec:
                  containers:
                    - name: test
                      image: node:14.4.0
                      tty: true
                    - name: app
                      image: ajarombek/jarombek-com
                      tty: true
                    - name: database
                      image: ajarombek/jarombek-com-database
                      tty: true
                  restartPolicy: Never
            """.stripIndent()
        }
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
        stage("Setup Project") {
            steps {
                script {
                    setupProjectScript()
                }
            }
        }
        stage("Execute Client Tests") {
            steps {
                script {
                    executeClientTestScript()
                }
            }
        }
        stage("Execute Server Tests") {
            steps {
                script {
                    executeServerTestScript()
                }
            }
        }
        stage("Execute End to End Tests") {
            steps {
                script {
                    executeE2ETestScript()
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
    def name = "jarombek-com"
    def branch = "master"

    container('test') {
        genericsteps.checkoutRepo(name, branch)
    }
}

def setupProjectScript() {
    container('test') {
        dir('repos/jarombek-com') {
            sh '''
                set +x
                node --version
                npm --version
                yarn --version
                
                yarn
            '''
        }
    }
}

def executeClientTestScript() {
    container('test') {
        dir('repos/jarombek-com') {
            try {
                CLIENT_STATUS = sh(
                    script: """#!/bin/bash
                        set +e
                        set -x
                        yarn client:test -- --updateSnapshot 2>&1 | tee test_results.log
                        exit_status=\${PIPESTATUS[0]}
                        chmod 777 test_results.log
            
                        exit \$exit_status
                    """,
                    returnStatus: true
                )
            } catch (Exception ex) {
                echo "Client Tests Failed"
                CLIENT_STATUS = 1
            }
        }
    }
}

def executeServerTestScript() {
    container('test') {
        dir('repos/jarombek-com') {
            try {
                SERVER_STATUS = sh(
                    script: """#!/bin/bash
                        set +e
                        set -x
                        yarn server:test -- --detectOpenHandles 2>&1 | tee -a test_results.log
                        exit_status=\${PIPESTATUS[0]}
            
                        exit \$exit_status
                    """,
                    returnStatus: true
                )
            } catch (Exception ex) {
                echo "Server Testing Failed"
                SERVER_STATUS = 1
            }
        }
    }
}

def executeE2ETestScript() {
    container('test') {
        dir('repos/jarombek-com') {
            try {
                E2E_STATUS = sh(
                    script: """#!/bin/bash
                        yarn cy:run-headless 2>&1 | tee -a test_results.log
                        exit_status=\${PIPESTATUS[0]}
            
                        exit \$exit_status
                    """,
                    returnStatus: true
                )
            } catch (Exception ex) {
                echo "E2E Testing Failed"
                E2E_STATUS = 1
            }
        }
    }

    // Determine the final result of the pipeline
    def result = CLIENT_STATUS + SERVER_STATUS + E2E_STATUS

    if (result > 0) {
        currentBuild.result = "UNSTABLE"
    }
}

def postScript() {
    def bodyContent = ""
    def testResultLog = readFile "repos/jarombek-com/test_results.log"

    testResultLog.split('\n').each {
        bodyContent += "<p style=\"font-family: Consolas,monaco,monospace\">$it</p>"
    }

    def bodyTitle = "Jarombek Com Application Tests"
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}
