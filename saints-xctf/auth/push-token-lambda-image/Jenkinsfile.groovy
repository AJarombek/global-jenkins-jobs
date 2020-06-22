/**
 * Jenkins script for pushing a Docker image that pushes a 'token' AWS Lambda function to DockerHub.
 * @author Andrew Jarombek
 * @since 6/21/2020
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
        stage("Push Docker Image") {
            steps {
                script {
                    pushImage()
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
    dir('repos/saints-xctf-auth') {
        git.basicClone('saints-xctf-auth', 'master')
    }
}

def pushImage() {
    dir("repos/saints-xctf-auth/token") {
        sh """
            sudo docker image build \
                -f ../Dockerfile \
                -t python-lambda-dist:latest \
                --network=host \
                --build-arg ZIP_FILENAME=SaintsXCTFRotate .
        """

        sh """
            sudo docker image build -t auth-saints-xctf-com-token:latest .
        """
    }

    withCredentials([
        usernamePassword(
            credentialsId: 'ajarombek-docker-hub',
            passwordVariable: 'dockerPassword',
            usernameVariable: 'dockerUsername'
        )
    ]) {
        sh 'sudo docker login -u $dockerUsername -p $dockerPassword'
    }

    sh """
        sudo docker image tag auth-saints-xctf-com-token:latest ajarombek/auth-saints-xctf-com-token:latest
        sudo docker push ajarombek/auth-saints-xctf-com-token:latest
        
        sudo docker image tag auth-saints-xctf-com-token:latest ajarombek/auth-saints-xctf-com-token:${params.label}
        sudo docker push ajarombek/auth-saints-xctf-com-token:${params.label}
    """
}

def postScript() {
    email.sendEmail(
        "Push SaintsXCTF Auth 'Token' AWS Lambda Docker image to DockerHub.",
        "",
        env.JOB_NAME,
        currentBuild.result,
        env.BUILD_NUMBER,
        env.BUILD_URL
    )

    cleanWs()
}
