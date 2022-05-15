/**
 * Jenkins script for creating AWS CloudWatch Synthetic Monitoring Canary functions.
 * @author Andrew Jarombek
 * @since 7/5/2021
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        kubernetes {
            yaml """\
                apiVersion: v1
                kind: Pod
                metadata:
                  name: saints-xctf-create-synthetic-monitoring
                  namespace: jenkins
                  labels:
                    version: v1.0.0
                    environment: development
                    application: saints-xctf-create-synthetic-monitoring
                spec:
                  containers:
                    - name: terraform
                      image: hashicorp/terraform:1.0.1
                      command: ["sleep", "infinity"]
                      tty: true
                  restartPolicy: Never
            """.stripIndent()
        }
    }
    parameters {
        booleanParam(
            name: 'autoApply',
            defaultValue: true,
            description: "Whether the Terraform infrastructure should be automatically approved."
        )
        choice(
            name: 'environment',
            choices: ['all', 'dev', 'prod'],
            description: 'Environment to build the infrastructure in.'
        )
    }
    options {
        ansiColor('xterm')
        timeout(time: 1, unit: 'HOURS')
        buildDiscarder(logRotator(daysToKeepStr: '10', numToKeepStr: '5'))
    }
    stages {
        stage("Clean Workspace") { steps { script { cleanWs() } } }
        stage("Checkout Repositories") { steps { script { checkoutRepos() } } }
        stage("Get Lambda Zip Files") {
            when {
                anyOf {
                    equals expected: 'dev', actual: params.environment
                    equals expected: 'prod', actual: params.environment
                }
            }
            steps { script { createLambdaZipFiles() } }
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
    }
    post {
        always {
            script {
                postScript()
            }
        }
    }
}

def checkoutRepos() {
    container('terraform') {
        genericsteps.checkoutRepo('saints-xctf-infrastructure', 'master')
    }
}

def createLambdaZipFiles() {
    container('terraform') {
        dir('repos/saints-xctf-infrastructure/synthetic-monitoring/modules/canaries') {
            dir('func/sign-in') {
                sh '''
                    zip -r9 SaintsXCTFSignIn.zip .
                    mv SaintsXCTFSignIn.zip ../../SaintsXCTFSignIn.zip
                '''
            }

            dir('func/up') {
                sh '''
                    zip -r9 SaintsXCTFUp.zip .
                    mv SaintsXCTFUp.zip ../../SaintsXCTFUp.zip
                '''
            }
        }
    }
}

def terraformInit() {
    INFRA_DIR = "repos/saints-xctf-infrastructure/synthetic-monitoring/env/$params.environment"

    container('terraform') {
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
        terraform.terraformApply(INFRA_DIR, params.autoApply)
    }
}

def postScript() {
    email.sendEmail(
        "Create ${params.environment.toUpperCase()} SaintsXCTF CloudWatch Synthetic Monitoring Infrastructure",
        "",
        env.JOB_NAME,
        currentBuild.result,
        env.BUILD_NUMBER,
        env.BUILD_URL
    )

    cleanWs()
}