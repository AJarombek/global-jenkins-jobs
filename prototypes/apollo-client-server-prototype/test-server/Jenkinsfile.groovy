/**
 * Jenkins script that tests the Apollo Server Node.js/Express codebase in the apollo-client-server-prototype application.
 * @author Andrew Jarombek
 * @since 7/27/2021
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        kubernetes {
            yaml """\
                apiVersion: v1
                kind: Pod
                metadata:
                  name: apollo-client-server-prototype-test-server
                  namespace: jenkins
                  labels:
                    version: v1.0.0
                    environment: development
                    application: apollo-client-server-prototype-test-server
                spec:
                  containers:
                    - name: apollo-prototype-server-app
                      image: ajarombek/apollo-client-server-prototype-api-app:latest
                      tty: true
                    - name: apollo-prototype-server
                      image: ajarombek/apollo-client-server-prototype-api-nginx:latest
                      tty: true
                    - name: apollo-prototype-database
                      image: ajarombek/apollo-client-server-prototype-database:latest
                      tty: true
                    - name: nodejs
                      image: node:14.16
                      tty: true
            """.stripIndent()
        }
    }
    options {
        ansiColor('xterm')
        timeout(time: 1, unit: 'HOURS')
        timestamps()
        buildDiscarder(
            logRotator(daysToKeepStr: '10', numToKeepStr: '5')
        )
    }
    stages {
        stage("Clean Workspace") { steps { script { cleanWs() } } }
        stage("Checkout Repository") { steps { script { checkoutRepo() } } }
        stage("Setup Application") { steps { script { setupApplication() } } }
        stage("Server Tests") { steps { script { runTests() } } }
    }
    post {
        always {
            script {
                postScript()
            }
        }
    }
}

// Stage functions
def checkoutRepo() {
    genericsteps.checkoutRepo("apollo-client-server-prototype", "master")
}

def setupApplication() {
    container('nodejs') {
        dir('repos/apollo-client-server-prototype/server') {
            sh '''
                npm install yarn -g
                
                # Equivalent to 'npm ci'
                yarn install --frozen-lockfile
            '''
        }
    }
}

def runTests() {
    container('nodejs') {
        dir('repos/apollo-client-server-prototype/server') {
            sh 'yarn test'
        }
    }
}

def postScript() {
    def bodyTitle = "Test Apollo Server in apollo-client-server-prototype."
    def bodyContent = ""
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}
