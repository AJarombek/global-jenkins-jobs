/**
 * Jenkins script that uses Terraform to create an S3 bucket that holds MySQL database backup files.
 * @author Andrew Jarombek
 * @since 7/26/2020
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        label 'master'
    }
    parameters {
        booleanParam(
            name: 'autoApply',
            defaultValue: true,
            description: "Whether the Terraform infrastructure should be automatically approved."
        )
        choice(
            name: 'environment',
            choices: ['dev', 'prod'],
            description: 'Environment to create the database backup S3 bucket.'
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
                    genericsteps.checkoutRepo("saints-xctf-infrastructure", "master")
                }
            }
        }
        stage("Terraform Init") {
            steps {
                script {
                    INFRA_DIR = "repos/saints-xctf-infrastructure/database-backup/env/$params.environment"
                    terraform.terraformInit(INFRA_DIR)
                }
            }
        }
        stage("Terraform Validate") {
            steps {
                script {
                    terraform.terraformValidate(INFRA_DIR)
                }
            }
        }
        stage("Terraform Plan") {
            steps {
                script {
                    terraform.terraformPlan(INFRA_DIR)
                }
            }
        }
        stage("Terraform Apply") {
            when {
                allOf {
                    environment name: 'TERRAFORM_NO_CHANGES', value: 'false'
                    environment name: 'TERRAFORM_PLAN_ERRORS', value: 'false'
                }
            }
            steps {
                script {
                    terraform.terraformApply(INFRA_DIR, params.autoApply)
                }
            }
        }
    }
    post {
        always {
            script {
                def bodyTitle = "Create saints-xctf-infrastructure $params.environment Database Backup."
                def bodyContent = ""
                def jobName = env.JOB_NAME
                def buildStatus = currentBuild.result
                def buildNumber = env.BUILD_NUMBER
                def buildUrl = env.BUILD_URL

                genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
            }
        }
    }
}