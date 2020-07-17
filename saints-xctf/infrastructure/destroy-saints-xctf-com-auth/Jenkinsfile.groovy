/**
 * Jenkins script for destroying AWS infrastructure for auth.saintsxctf.com.
 * @author Andrew Jarombek
 * @since 6/20/2020
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
        choice(
            name: 'environment',
            choices: ['dev', 'prod'],
            description: 'Environment to build the infrastructure in.'
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

def checkoutRepo() {
    def name = "saints-xctf-infrastructure"
    def branch = "master"

    genericsteps.checkoutRepo(name, branch)
}

def terraformInit() {
    INFRA_DIR = "repos/saints-xctf-infrastructure/saints-xctf-com-auth/env/$params.environment"
    terraform.terraformInit(INFRA_DIR)
}

def terraformValidate() {
    terraform.terraformValidate(INFRA_DIR)
}

def terraformPlanDestroy() {
    terraform.terraformPlanDestroy(INFRA_DIR)
}

def terraformDestroy() {
    terraform.terraformDestroy(INFRA_DIR, params.autoDestroy)
}

def postScript() {
    email.sendEmail(
        "Destroy SaintsXCTF Auth AWS Infrastructure",
        "",
        env.JOB_NAME,
        currentBuild.result,
        env.BUILD_NUMBER,
        env.BUILD_URL
    )

    cleanWs()
}