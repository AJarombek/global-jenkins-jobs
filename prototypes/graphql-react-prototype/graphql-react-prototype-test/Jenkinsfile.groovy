/**
 * Jenkins script for testing the graphql-react-prototype application.
 * @author Andrew Jarombek
 * @since 4/18/2020
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        kubernetes {
            yaml '''
apiVersion: v1
kind: Pod
metadata:
  name: graphql-react-prototype-test
  namespace: jenkins
  labels:
    version: v1.0.0
    environment: development
    application: graphql-react-prototype-test
spec:
  containers:
    - name: test
      image: 739088120071.dkr.ecr.us-east-1.amazonaws.com/graphql-react-prototype-base:latest
      tty: true
            '''
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
        stage("Clean Workspace") { steps { script { cleanWs() } } }
        stage("Update Docker Image") { steps { script { updateDockerImage() } } }
        stage("Execute Tests") { steps { script { executeTestScript() } } }
    }
    post {
        always {
            script {
                postScript()
            }
        }
    }
}

def updateDockerImage() {
    build(
        job: 'prototypes/graphql-react-prototype/push-base-image',
        propagate: false,
        wait: true
    )
}

def executeTestScript() {
    test_result = 0
    container('test') {
        try {
            test_result = sh(
                script: """#!/bin/bash
                    cd /src
                    npm test 2>&1 | tee test_results.log
                    exit \${PIPESTATUS[0]}
                """,
                returnStatus: true
            )
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

    container('test') {
        testResultLog = sh(
            script: "cat /src/test_results.log",
            returnStdout: true
        )
    }

    testResultLog.split('\n').each {
        bodyContent += "<p style=\"font-family: Consolas,monaco,monospace\">$it</p>"
    }

    def bodyTitle = "GraphQL React Prototype Tests"
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}
