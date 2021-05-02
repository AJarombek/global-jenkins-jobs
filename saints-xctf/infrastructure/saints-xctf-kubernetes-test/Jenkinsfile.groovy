/**
 * Jenkins script for testing the Kubernetes infrastructure defined in the saints-xctf-infrastructure repository.
 * @author Andrew Jarombek
 * @since 2/21/2021
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        kubernetes {
            yaml '''
apiVersion: v1
kind: Pod
metadata:
  name: saints-xctf-kubernetes-test
  namespace: jenkins
  labels:
    version: v1.0.0
    environment: all
    application: saints-xctf-kubernetes-test
spec:
  containers:
    - name: go
      image: golang:1.14.4-buster
      tty: true
  serviceAccountName: jenkins-kubernetes-test
  automountServiceAccountToken: true
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
        stage("Checkout Repository") { steps { script { checkoutRepo() } } }
        stage("Execute Kubernetes Tests") { steps { script { executeTestScript() } } }
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
    container('go') {
        dir('repos/saints-xctf-infrastructure') {
            git.basicClone('saints-xctf-infrastructure', 'master')
        }
    }
}

def executeTestScript() {
    container('go') {
        dir('repos/saints-xctf-infrastructure/test-k8s') {
            def testEnv = getEnv()
            sh """
                export TEST_ENV=$testEnv
                # go test --incluster true -v
            """
        }
    }
}

def postScript() {
    def bodyTitle = "SaintsXCTF Kubernetes Infrastructure Tests"
    def bodyContent = ""
    def jobName = getJobName()
    def buildStatus = currentBuild.result
    def buildNumber = getBuildNumber()
    def buildUrl = ''

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}

@NonCPS
def getEnv() {
    def matches = JOB_NAME =~ /saints-xctf-kubernetes-test-(\w+)/
    return matches[0][1]
}

@NonCPS
def getJobName() {
    return JOB_NAME
}

@NonCPS
def getBuildNumber() {
    return BUILD_NUMBER
}