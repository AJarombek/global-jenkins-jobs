/**
 * Jenkins script that pushes a Docker image to DockerHub for the jarombek.com application.
 * @author Andrew Jarombek
 * @since 7/26/2021
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        label 'master'
    }
    parameters {
        choice(
            name: 'image',
            choices: ['jarombek-com-database', 'jarombek-com'],
            description: 'Name of the Docker image and DockerHub repository to push to.'
        )
        string(
            name: 'label',
            defaultValue: '1.1.0',
            description: 'Label/Version of the Docker image to push to DockerHub'
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
        buildDiscarder(
            logRotator(daysToKeepStr: '10', numToKeepStr: '5')
        )
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
            script {
                postScript()
            }
        }
    }
}

def checkoutRepo() {
    genericsteps.checkoutRepo(params.image, "master")
}

def buildImage() {
    dir("repos/$params.image") {
        sh """
            sudo docker image build \
                -t $params.image:latest \
                --network=host .
        """
    }
}

def pushImage() {
    def imageName = params.image
    def imageLabel = params.label
    def isLatest = params.isLatest

    dockerhub.auth()
    dockerhub.pushImage(imageName, imageLabel)

    if (isLatest) {
        dockerhub.pushImage(imageName)
    }
}

def cleanupDockerEnvironment() {
    def imageName = params.image

    sh """
        sudo docker image rm $imageName:latest
        sudo docker image rm ajarombek/$imageName:$params.label
    """

    if (params.isLatest) {
        sh """
            sudo docker image rm ajarombek/$imageName:latest
        """
    }

    sh "sudo docker image ls"
}

def postScript() {
    def bodyTitle = "Push jarombek.com Docker image to DockerHub."
    def bodyContent = "Image pushed: $params.image"
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}
