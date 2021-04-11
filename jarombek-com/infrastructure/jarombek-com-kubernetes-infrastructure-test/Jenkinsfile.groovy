/**
 * Jenkins script for testing the Kubernetes infrastructure defined in the jarombek-com-infrastructure repository.
 * @author Andrew Jarombek
 * @since 4/11/2021
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        kubernetes {
            yaml '''
apiVersion: v1
kind: Pod
metadata:
  name: jarombek-com-kubernetes-infrastructure-test
  namespace: jenkins
  labels:
    version: v1.0.0
    environment: development
    application: jarombek-com-kubernetes-infrastructure-test
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
        stage("Execute Kubernetes Tests") {
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
    container('go') {
        dir('repos/jarombek-com-infrastructure') {
            git.basicClone('jarombek-com-infrastructure', 'master')
        }
    }
}

def executeTestScript() {
    container('go') {
        dir('repos/jarombek-com-infrastructure/test-k8s') {
            def testEnv = getEnv()
            sh """
                export TEST_ENV=$testEnv
                go test --incluster true
            """
        }
    }
}

def postScript() {
    def bodyTitle = "jarombek.com Kubernetes Infrastructure Tests"
    def bodyContent = ""
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}

@NonCPS
def getEnv() {
    def matches = JOB_NAME =~ /jarombek-com-kubernetes-test-(\w+)/
    return matches[0][1]
}
