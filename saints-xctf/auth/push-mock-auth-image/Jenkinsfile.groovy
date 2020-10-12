/**
 * Jenkins script to push a mock implementation image to Dockerhub for the saints-xctf-auth application.
 * @author Andrew Jarombek
 * @since 10/11/2020
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
    genericsteps.checkoutRepo("saints-xctf-auth", "master")
}

def buildImage() {
    dir("repos/saints-xctf-auth") {
        withCredentials([
            usernamePassword(
                credentialsId: 'saintsxctf-andy',
                passwordVariable: 'password',
                usernameVariable: 'username'
            )
        ]) {
            sh """
                sudo docker image build \
                    --build-arg SXCTF_AUTH_ID=$username \
                    --build-arg SXCTF_AUTH_SECRET=$password \
                    -f mock/Dockerfile \
                    -t $params.image:latest \
                    --network=host .
            """
        }
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
    def bodyTitle = "Push SaintsXCTF Auth Mock Docker image to Dockerhub."
    def bodyContent = ""
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}
