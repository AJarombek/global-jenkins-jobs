/**
 * Jenkins script for testing the Global AWS infrastructure.
 * @author Andrew Jarombek
 * @since 3/24/2019
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        label 'master'
    }
    parameters {
        string(
            name: 'branch',
            defaultValue: 'master',
            description: 'Git branch to execute tests from'
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
                    checkoutRepo(params.branch)
                }
            }
        }
        stage("Setup Environment") {
            steps {
                script {
                    setupEnvironment()
                }
            }
        }
        stage("Execute Infrastructure Tests") {
            steps {
                script {
                    executeTests()
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

def checkoutRepo(String branch) {
    dir('repos/global-aws-infrastructure') {
        git.basicClone('global-aws-infrastructure', branch)
    }
}

def setupEnvironment() {
    dir('repos/global-aws-infrastructure/test') {
        sh '''
            set +e
            set -x
            python3.8 --version
            python3.8 -m pip --version

            sudo pip3 install pipenv
            pipenv --rm
            pipenv install
        '''
    }
}

def executeTests() {
    dir('repos/global-aws-infrastructure/test') {
        try {
            ansiColor('xterm') {
                def status = sh (
                    script: "#!/bin/bash \n" +
                    """
                        # The AWS SDK needs to know which region the infrastructure is in.
                        export AWS_DEFAULT_REGION=us-east-1
                        
                        pipenv run python runner.py test_results.log
                        exit_status=\$?

                        cat test_results.log

                        deactivate
                        exit \$exit_status
                    """,
                    returnStatus: true
                )

                if (status >= 1) {
                    currentBuild.result = "UNSTABLE"
                } else {
                    currentBuild.result = "SUCCESS"
                }
            }
        } catch (Exception ex) {
            echo "Infrastructure Testing Failed"
            currentBuild.result = "FAILURE"
        }
    }
}

def postScript() {
    def bodyTitle = "Global Infrastructure Tests"
    def bodyContent = ""
    def testResultLog = ""

    dir("test") {
        testResultLog = readFile "test_results.log"
    }

    testResultLog.split('\n').each {
        bodyContent += "<p style=\"font-family: Consolas,monaco,monospace\">$it</p>"
    }

    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}
