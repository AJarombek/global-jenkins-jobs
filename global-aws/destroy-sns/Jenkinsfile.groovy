/**
 * Jenkins script that uses Terraform to destroy global AWS SNS topics and subscriptions.
 * @author Andrew Jarombek
 * @since 7/15/2021
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
        stage("Clean Workspace") { steps { script { cleanWs() } } }
        stage("Checkout Repository") { steps { script { checkoutRepo() } } }
        stage("Terraform Init") { steps { script { terraformInit() } } }
        stage("Terraform Plan") { steps { script { terraformPlanDestroy() } } }
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
    def name = "global-aws-infrastructure"
    def branch = "master"

    genericsteps.checkoutRepo(name, branch)
}

def terraformInit() {
    INFRA_DIR = "repos/global-aws-infrastructure/sns"
    terraform.terraformInit(INFRA_DIR)
}

def terraformPlanDestroy() {
    terraform.terraformPlanDestroy(INFRA_DIR)
}

def terraformDestroy() {
    terraform.terraformDestroy(INFRA_DIR, params.autoDestroy)
}

def postScript() {
    def bodyTitle = "Destroy global-aws-infrastructure SNS Resources."
    def bodyContent = ""
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}
