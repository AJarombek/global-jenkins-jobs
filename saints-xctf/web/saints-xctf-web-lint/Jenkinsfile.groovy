/**
 * Jenkins script for linting saints-xctf-web.
 * @author Andrew Jarombek
 * @since 3/14/2020
 */

// 15 West 64th Street, Begin setting up furniture, Too many IKEA trips

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        kubernetes {
            defaultContainer 'nodejs'
            yaml """\
                apiVersion: v1
                kind: Pod
                metadata:
                  name: saints-xctf-web-lint
                  namespace: jenkins
                  labels:
                    version: v1.0.0
                    environment: all
                    application: saints-xctf-web-lint
                spec:
                  containers:
                    - name: nodejs
                      image: node:14.4.0
                      tty: true
                  serviceAccountName: jenkins-kubernetes-test
                  automountServiceAccountToken: true
                  restartPolicy: Never
            """.stripIndent()
        }
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
        stage("Setup Project") { steps { script { setupProject() } } }
        stage("Lint Code") { steps { script { lintCode() } } }
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
    genericsteps.checkoutRepo("saints-xctf-web", "master")
}

def setupProject() {
    dir('repos/saints-xctf-web') {
        sh 'yarn'
    }
}

def lintCode() {
    dir('repos/saints-xctf-web') {
        sh 'yarn lint --max-warnings=5'
    }
}

def postScript() {
    def bodyTitle = "SaintsXCTF Web Linting"
    def bodyContent = ""
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}

