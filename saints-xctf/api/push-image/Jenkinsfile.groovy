/**
 * Jenkins script that pushes a Docker image to ECR for the SaintsXCTF API.
 * @author Andrew Jarombek
 * @since 9/20/2020
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        label 'master'
    }
    parameters {
        choice(
            name: 'image',
            choices: ['saints-xctf-api-flask', 'saints-xctf-api-nginx'],
            description: 'Name of the Docker image and ECR repository to push to.'
        )
        string(
            name: 'label',
            defaultValue: '1.0.0',
            description: 'Label/Version of the Docker image to push to ECR'
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
    genericsteps.checkoutRepo("saints-xctf-api", "master")
}

def buildImage() {
    dir("repos/saints-xctf-api/api/src") {
        def dockerfile = params.image == 'saints-xctf-api-flask' ? 'api.flask.dockerfile' : 'api.nginx.dockerfile'

        if (params.image == 'saints-xctf-api-flask') {
            withCredentials([
                string(credentialsId: 'aws-access-key-id', variable: 'aws_access_key_id'),
                string(credentialsId: 'aws-secret-access-key', variable: 'aws_secret_access_key')
            ]) {
                sh """
                    touch credentials
                    echo "[default]" >> credentials
                    echo "aws_access_key_id = $aws_access_key_id" >> credentials
                    echo "aws_secret_access_key = $aws_secret_access_key" >> credentials
                """
            }
        }

        sh """
            sudo docker image build \
                -f $dockerfile \
                -t $params.image:latest \
                --network=host .
        """
    }
}

def pushImage() {
    def repoUrl = "739088120071.dkr.ecr.us-east-1.amazonaws.com"
    def imageName = params.image
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
    def imageName = params.image
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
    def bodyTitle = "Push SaintsXCTF API Docker image to ECR."
    def bodyContent = "Image pushed: $params.image"
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}
