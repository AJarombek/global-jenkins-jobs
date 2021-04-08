/**
 * Jenkins script that tests the Apollo Client React codebase in the apollo-client-server-prototype application.
 * @author Andrew Jarombek
 * @since 4/7/2021
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        label 'master'
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
        stage("Lint Codebase") { steps { script { runLinter() } } }
        stage("End to End Tests") { steps { script { runE2eTests() } } }
        stage("Image Snapshot Tests") { steps { script { runPictureSnapshotTests() } } }
    }
    post {
        always {
            script {
                postScript()
            }
        }
    }
}

// S,J -> Lisa
// _,J -> _

// Stage functions
def checkoutRepo() {
    def name = "apollo-client-server-prototype"
    def branch = "master"

    genericsteps.checkoutRepo(name, branch)
}

def setupApplication() {
    container('nodejs') {
        dir('repos/apollo-client-server-prototype/client') {
            sh '''
                npm install yarn -g
                
                # Equivalent to 'npm ci'
                yarn install --frozen-lockfile
            '''
        }
    }
}

def runLinter() {
    container('nodejs') {
        dir('repos/apollo-client-server-prototype/client') {
            sh 'yarn check-format'
        }
    }
}

def runE2eTests() {
    container('cypress') {
        dir('repos/apollo-client-server-prototype/client') {
            sh '''
                npm install yarn -g
                yarn cy:run-headless
            '''
        }
    }
}

def runPictureSnapshotTests() {
    container('nodejs') {
        dir('repos/apollo-client-server-prototype/client') {
            sh 'yarn test'
        }
    }
}

def postScript() {
    def bodyTitle = "Test Apollo Client in apollo-client-server-prototype."
    def bodyContent = ""
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}
