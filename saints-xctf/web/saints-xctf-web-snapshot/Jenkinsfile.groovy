/**
 * Jenkins script for snapshot testing saints-xctf-web.
 * @author Andrew Jarombek
 * @since 4/26/2021
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        kubernetes {
            defaultContainer 'nodejs'
            yaml """\
                apiVersion: v1
                kind: Pod
                metadata:
                  name: saints-xctf-web-snapshot
                  namespace: jenkins
                  labels:
                    version: v1.0.0
                    environment: all
                    application: saints-xctf-web-snapshot
                spec:
                  containers:
                    - name: nodejs
                      image: node:14.4.0
                      tty: true
                  serviceAccountName: jenkins-kubernetes-test
                  automountServiceAccountToken: true
            """.stripIndent()
        }
    }
    triggers {
        cron('H 2 * * *')
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
        stage("Snapshot Test Components") { steps { script { runSnapshotTests() } } }
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

def runSnapshotTests() {
    dir('repos/saints-xctf-web') {
        sh '''
            yarn test:snapshot --coverage=false 2>&1 | tee test_results.log
            exit_status=${PIPESTATUS[0]}
            exit $exit_status
        '''
    }
}

def postScript() {
    def bodyContent = ""
    def testResultLog

    dir('repos/saints-xctf-web') {
        testResultLog = readFile "test_results.log"
    }

    testResultLog.split('\n').each {
        bodyContent += "<p style=\"font-family: Consolas,monaco,monospace\">$it</p>"
    }

    def bodyTitle = "SaintsXCTF Web Snapshot Tests"
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}

