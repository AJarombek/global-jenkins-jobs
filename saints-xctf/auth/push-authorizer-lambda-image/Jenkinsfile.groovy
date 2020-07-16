/**
 * Jenkins script for pushing a Docker image that pushes an 'authorizer' AWS Lambda function to DockerHub.
 * @author Andrew Jarombek
 * @since 6/23/2020
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
        stage("Cleanup Docker Images") {
            steps {
                script {
                    cleanupImages()
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
    def name = "saints-xctf-auth"
    def branch = "master"

    genericsteps.checkoutRepo(name, branch)
}

def buildImage() {
    def subDir = "token"
    def zipFilename = "SaintsXCTFAuthorizer"
    def imageName = "auth-saints-xctf-com-authorizer"

    saintsxctfsteps.buildImage(subDir, zipFilename, imageName)
}

def pushImage() {
    def imageName = "auth-saints-xctf-com-authorizer"
    def imageLabel = params.label
    def isLatest = params.isLatest

    saintsxctfsteps.pushImage(imageName, imageLabel, isLatest)
}

def cleanupImages() {
    def imageName = "auth-saints-xctf-com-authorizer"
    def imageLabel = params.label
    def isLatest = params.isLatest

    saintsxctfsteps.cleanupImages(imageName, imageLabel, isLatest)
}

def postScript() {
    def bodyTitle = "Push SaintsXCTF Auth 'Authorizer' AWS Lambda Docker image to DockerHub."
    def bodyContent = ""
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}
