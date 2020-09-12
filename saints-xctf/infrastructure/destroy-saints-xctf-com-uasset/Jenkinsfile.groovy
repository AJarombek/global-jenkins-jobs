/**
 * Jenkins script that destroys an AWS S3 bucket for SaintsXCTF user assets.
 * @author Andrew Jarombek
 * @since 9/12/2020
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        label 'master'
    }
    parameters {
        booleanParam(
            name: 'autoDestroy',
            defaultValue: true,
            description: "Whether the Terraform infrastructure should be automatically destroyed."
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
        stage("Terraform Init") {
            steps {
                script {
                    terraformInit()
                }
            }
        }
        stage("Terraform Plan") {
            steps {
                script {
                    terraformPlanDestroy()
                }
            }
        }
        stage("Terraform Destroy") {
            when {
                allOf {
                    environment name: 'TERRAFORM_NO_CHANGES', value: 'false'
                    environment name: 'TERRAFORM_PLAN_ERRORS', value: 'false'
                }
            }
            steps {
                script {
                    terraformDestroy()
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

// Stage functions
def checkoutRepo() {
    def name = "saints-xctf-infrastructure"
    def branch = "master"

    genericsteps.checkoutRepo(name, branch)
}

def terraformInit() {
    INFRA_DIR = "repos/saints-xctf-infrastructure/saints-xctf-com-uasset"
    terraform.terraformInit(INFRA_DIR)
}

def terraformPlanDestroy() {
    terraform.terraformPlanDestroy(INFRA_DIR)
}

def terraformDestroy() {
    terraform.terraformDestroy(INFRA_DIR, params.autoDestroy)
}

def postScript() {
    def bodyTitle = "Destroy the saints-xctf-infrastructure user asset S3 bucket."
    def bodyContent = ""
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}
