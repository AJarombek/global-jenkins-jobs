/**
 * Jenkins script that pushes a base Docker image for the graphql-react-prototype application.
 * @author Andrew Jarombek
 * @since 6/28/2020
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
        stage("Add Env File") {
            steps {
                script {
                    addEnvFile()
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
    def name = "graphql-react-prototype"
    def branch = "master"

    genericsteps.checkoutRepo(name, branch)
}

def addEnvFile() {
    dir("repos/graphql-react-prototype") {
        withCredentials([string(credentialsId: 'ajarombek-github-access-token', variable: 'accessToken')]) {
            sh """
                touch .env
                echo "GITHUB_ACCESS_TOKEN=$accessToken" >> .env
            """
        }
    }
}

def buildImage() {
    dir("repos/graphql-react-prototype") {
        sh """
            sudo docker image build \
                -f base.dockerfile \
                -t graphql-react-prototype-base:latest \
                --network=host .
        """
    }
}

def pushImage() {
    def repoUrl = "739088120071.dkr.ecr.us-east-1.amazonaws.com"
    def imageName = "graphql-react-prototype-base"
    def imageLabel = params.label
    def isLatest = params.isLatest

    sh """
        aws ecr get-login-password --region us-east-1 | sudo docker login -u AWS --password-stdin $repoUrl
    
        sudo docker image tag $imageName:latest $repoUrl/$imageName:$imageLabel
        sudo docker push $repoUrl/$imageName:$imageLabel
    """

    if (isLatest) {
        sh """
            sudo docker image tag $imageName:latest $repoUrl/$imageName:latest
            sudo docker push $repoUrl/$imageName:latest
        """
    }
}

def cleanupDockerEnvironment() {
    def imageName = "graphql-react-prototype-base"
    def repoUrl = "739088120071.dkr.ecr.us-east-1.amazonaws.com"

    sh """
        sudo docker image rm $imageName:latest
        sudo docker image rm $repoUrl/$imageName:$params.label
    """

    if (params.isLatest) {
        sh """
            sudo docker image rm $repoUrl/$imageName:latest
        """
    }

    sh "sudo docker image ls"
}

def postScript() {
    def bodyTitle = "Push graphql-react-prototype-base Docker image to ECR."
    def bodyContent = ""
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}
