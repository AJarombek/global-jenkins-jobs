/**
 * Jenkins script that pushes a 'token' AWS Lambda function to ECR.
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

def buildImage() {
    def imageName = "graphql-react-prototype-base"

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

    sh """
        sudo docker image rm $imageName:latest
    """
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
