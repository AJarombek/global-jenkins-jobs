/**
 * Jenkins script for testing the Kubernetes infrastructure defined in the global-aws-infrastructure repository.
 * @author Andrew Jarombek
 * @since 7/6/2020
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        kubernetes {
            yaml '''
apiVersion: v1
kind: Pod
metadata:
  name: global-kubernetes-infrastructure-test
  namespace: jenkins
  labels:
    version: v1.0.0
    environment: development
    application: global-kubernetes-infrastructure-test
spec:
  containers:
    - name: go
      image: golang:1.14.4-buster
      tty: true
  serviceAccountName: jenkins-kubernetes-test
  automountServiceAccountToken: true
  restartPolicy: Never
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
        dir('repos/global-aws-infrastructure') {
            git.basicClone('global-aws-infrastructure', 'master')
        }
    }
}

def executeTestScript() {
    container('go') {
        dir('repos/global-aws-infrastructure/test-k8s') {
            sh '''
                go test --incluster true
            '''
        }
    }
}

def postScript() {
    def bodyTitle = "Global Kubernetes Infrastructure Tests"
    def bodyContent = ""
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}
