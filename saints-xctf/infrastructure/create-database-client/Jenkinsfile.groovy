/**
 * Jenkins script for creating Kubernetes and AWS infrastructure for the SaintsXCTF database client (db.saintsxctf.com).
 * @author Andrew Jarombek
 * @since 3/17/2021
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
        string(
            name: 'cidr',
            defaultValue: '0.0.0.0/0',
            description: 'CIDR block that has access to the SaintsXCTF database client (the db.saintsxctf.com domain).'
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
        stage("Terraform Validate") { steps { script { terraformValidate() } } }
        stage("Terraform Plan") { steps { script { terraformPlan() } } }
        stage("Terraform Apply") {
            when {
                allOf {
                    environment name: 'TERRAFORM_NO_CHANGES', value: 'false'
                    environment name: 'TERRAFORM_PLAN_ERRORS', value: 'false'
                }
            }
            steps {
                script {
                    terraformApply()
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
    genericsteps.checkoutRepo('saints-xctf-infrastructure', 'master')
}

def terraformInit() {
    INFRA_DIR = "repos/saints-xctf-infrastructure/database-client/$params.environment"
    terraform.terraformInit(INFRA_DIR)
}

def terraformValidate() {
    terraform.terraformValidate(INFRA_DIR)
}

def terraformPlan() {
    terraform.terraformPlan(
        INFRA_DIR,
        "terraform plan -var 'db_client_access_cidr=$params.cidr' -detailed-exitcode -out=terraform.tfplan"
    )
}

def terraformApply() {
    if (!params.autoApply) {
        timeout(time: 15, unit: 'MINUTES') {
            input message: 'Confirm Plan', ok: 'Apply'
        }
    }

    dir(INFRA_DIR) {
        sh "terraform apply -var 'db_client_access_cidr=$params.cidr' -auto-approve terraform.tfplan"
    }
}

def postScript() {
    def bodyTitle = "Create SaintsXCTF Database Client AWS & Kubernetes Infrastructure"
    def bodyContent = ""
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}