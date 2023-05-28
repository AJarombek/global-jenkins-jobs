/**
 * Jenkins script that pushes Docker images to Dockerhub used globally.
 * @author Andrew Jarombek
 * @since 10/10/2020
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        label 'master'
    }
    parameters {
        choice(
            name: 'image',
            choices: ['mysql-aws', 'pipenv-flask'],
            description: 'Name of the Docker image to push to Dockerhub.'
        )
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
        stage("Build Docker Image") {
            steps {
                script {
                    buildImage()
                }
            }
        }
        stage("Push Docker Image") {
            steps {
                script {
                    pushImage()
                }
            }
        }
        stage("Cleanup Docker Environment") {
            steps {
                script {
                    cleanupDockerEnvironment()
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
    genericsteps.checkoutRepo("global-aws-infrastructure", "main")
}

def buildImage() {
    dir("repos/global-aws-infrastructure/dockerfiles") {
        def dockerfileMap = [
            'mysql-aws': 'mysql-aws.dockerfile',
            'pipenv-flask': 'pipenv-flask.dockerfile'
        ]

        sh """
            sudo docker image build \
                -f ${dockerfileMap[params.image]} \
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
    def bodyTitle = "Push Global Dockerfile Image to Dockerhub."
    def bodyContent = "Image pushed: $params.image"
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}
