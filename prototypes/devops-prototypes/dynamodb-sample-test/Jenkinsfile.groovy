/**
 * Jenkins script for testing the DynamoDB DevOps Prototype sample code.
 * @author Andrew Jarombek
 * @since 2/27/2021
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        kubernetes {
            yaml '''
apiVersion: v1
kind: Pod
metadata:
  name: dynamodb-sample-test
  namespace: jenkins
  labels:
    version: v1.0.0
    environment: all
    application: dynamodb-sample-test
spec:
  containers:
    - name: go
      image: golang:1.14.4-buster
      tty: true
    - name: python
      image: python:3.9
      tty: true
    - name: terraform
      image: hashicorp/terraform:0.15.0
      command: [ "sleep", "infinity" ]
      tty: true
  serviceAccountName: jenkins-kubernetes-test
  automountServiceAccountToken: true
            '''
        }
    }
    triggers {
        cron('H 0 * * *')
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
        stage("Initial Terraform Plan Destroy") { steps { script { terraformPlanDestroy() } } }
        stage("Initial Terraform Destroy") {
            when {
                allOf {
                    environment name: 'TERRAFORM_NO_CHANGES', value: 'false'
                    environment name: 'TERRAFORM_PLAN_ERRORS', value: 'false'
                }
            }
            steps { script { terraformDestroy() } }
        }
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
            steps { script { terraformApply() } }
        }
        stage("Execute DynamoDB Tests for Terraform Changes") { steps { script { executeTerraformChangeTests() } } }
        stage("Run DynamoDB Go Script") { steps { script { executeGoScript() } } }
        stage("Execute DynamoDB Tests for Go Changes") { steps { script { executeGoChangeTests() } } }
        stage("Terraform Plan Destroy") { steps { script { terraformPlanDestroy() } } }
        stage("Terraform Destroy") {
            when {
                allOf {
                    environment name: 'TERRAFORM_NO_CHANGES', value: 'false'
                    environment name: 'TERRAFORM_PLAN_ERRORS', value: 'false'
                }
            }
            steps { script { terraformDestroy() } }
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
    dir('repos/devops-prototypes') {
        git.basicClone('devops-prototypes', 'master')
    }
}

def terraformInit() {
    container('terraform') {
        INFRA_DIR = "repos/devops-prototypes/samples/dynamodb/infra"
        terraform.terraformInit(INFRA_DIR)
    }
}

def terraformValidate() {
    container('terraform') {
        terraform.terraformValidate(INFRA_DIR)
    }
}

def terraformPlan() {
    container('terraform') {
        terraform.terraformPlan(INFRA_DIR)
    }
}

def terraformApply() {
    container('terraform') {
        terraform.terraformApply(INFRA_DIR, true)
    }
}

def executeTerraformChangeTests() {
    container('python') {
        sh "pip install pipenv"

        dir('repos/devops-prototypes/samples/dynamodb/test') {
            sh """
                pipenv install
                export AWS_DEFAULT_REGION=us-east-1
                pipenv run python testTerraformInfra.py
            """
        }
    }
}

def executeGoScript() {
    container('go') {
        dir('repos/devops-prototypes/samples/dynamodb/app') {
            sh """
                export AWS_REGION=us-east-1
                go run main.go
            """
        }
    }
}

def executeGoChangeTests() {
    container('python') {
        dir('repos/devops-prototypes/samples/dynamodb/test') {
            sh """
                export AWS_DEFAULT_REGION=us-east-1
                pipenv run python testGoSdkInfra.py
            """
        }
    }
}

def terraformPlanDestroy() {
    container('terraform') {
        terraform.terraformPlanDestroy(INFRA_DIR)
    }
}

def terraformDestroy() {
    container('terraform') {
        terraform.terraformDestroy(INFRA_DIR, true)
    }
}

def postScript() {
    def bodyTitle = "DevOps Prototype DynamoDB Sample Test"
    def bodyContent = ""
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}