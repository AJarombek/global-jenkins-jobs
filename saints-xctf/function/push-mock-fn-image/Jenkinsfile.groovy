/**
 * Jenkins script to push a mock implementation image to Dockerhub for the saints-xctf-functions application.
 * @author Andrew Jarombek
 * @since 3/28/2021
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        label 'master'
    }
    parameters {
        string(
            name: 'label',
            defaultValue: '1.0.0',
            description: 'Label/Version of the Docker image to push to Dockerhub'
        )
        booleanParam(
            name: 'isLatest',
            defaultValue: true,
            description: "Whether this Docker image should also be pushed with the 'latest' label"
        )
    }
    options {
        ansiColor('xterm')
        timeout(time: 1, unit: 'HOURS')
        timestamps()
        buildDiscarder(logRotator(daysToKeepStr: '10', numToKeepStr: '5'))
    }
    stages {
        stage("Clean Workspace") { steps { script { cleanWs() } } }
        stage("Checkout Repository") { steps { script { checkoutRepo() } } }
        stage("Build Docker Image") { steps { script { buildImage() } } }
        stage("Push Docker Image") { steps { script { pushImage() } } }
        stage("Cleanup Docker Environment") { steps { script { cleanupDockerEnvironment() } } }
    }
    post {
        always {
            script { postScript() }
        }
    }
}

def checkoutRepo() {
    genericsteps.checkoutRepo("saints-xctf-functions", "main")
}

def buildImage() {
    dir("repos/saints-xctf-functions/mock") {
        sh """
            sudo docker image build \
                --build-arg ENV=local \
                -t mock-saints-xctf-functions:latest \
                --network=host .
        """
    }
}

def pushImage() {
    def imageLabel = params.label
    def isLatest = params.isLatest

    dockerhub.auth()
    dockerhub.pushImage('mock-saints-xctf-functions', imageLabel)

    if (isLatest) {
        dockerhub.pushImage('mock-saints-xctf-functions')
    }
}

def cleanupDockerEnvironment() {
    sh """
        sudo docker image rm mock-saints-xctf-functions:latest
        sudo docker image rm ajarombek/mock-saints-xctf-functions:$params.label
    """

    if (params.isLatest) {
        sh """
            sudo docker image rm ajarombek/mock-saints-xctf-functions:latest
        """
    }

    sh "sudo docker image ls"
}

def postScript() {
    def bodyTitle = "Push SaintsXCTF Functions Mock Docker image to Dockerhub."
    def bodyContent = ""
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}
