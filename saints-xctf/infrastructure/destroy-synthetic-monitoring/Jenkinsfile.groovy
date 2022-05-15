/**
 * Jenkins script for destroying AWS CloudWatch Synthetic Monitoring Canary functions.
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
                  name: saints-xctf-destroy-synthetic-monitoring
                  namespace: jenkins
                  labels:
                    version: v1.0.0
                    environment: development
                    application: saints-xctf-destroy-synthetic-monitoring
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
            name: 'autoDestroy',
            defaultValue: true,
            description: "Whether the Terraform infrastructure should be automatically destroyed."
        )
        choice(
            name: 'environment',
            choices: ['all', 'dev', 'prod'],
            description: 'Environment to destroy the infrastructure in.'
        )
    }
    options {
        ansiColor('xterm')
        timeout(time: 1, unit: 'HOURS')
        buildDiscarder(logRotator(daysToKeepStr: '10', numToKeepStr: '5'))
    }
    stages {
        stage("Clean Workspace") { steps { script { cleanWs() } } }
        stage("Checkout Repository") { steps { script { checkoutRepo() } } }
        stage("Create ZIP Files") {
            when {
                anyOf {
                    equals expected: 'dev', actual: params.environment
                    equals expected: 'prod', actual: params.environment
                }
            }
            steps { script { mockZipFiles() } }
        }
        stage("Terraform Init") { steps { script { terraformInit() } } }
        stage("Terraform Plan") { steps { script { terraformPlanDestroy() } } }
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
    container('terraform') {
        genericsteps.checkoutRepo("saints-xctf-infrastructure", "master")
    }
}

def mockZipFiles() {
    container('terraform') {
        dir('repos/saints-xctf-infrastructure/synthetic-monitoring/modules/canaries') {
            sh '''
                touch SaintsXCTFSignIn.zip
                touch SaintsXCTFUp.zip
            '''
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

def terraformPlanDestroy() {
    container('terraform') {
        terraform.terraformPlanDestroy(INFRA_DIR)
    }
}

def terraformDestroy() {
    container('terraform') {
        terraform.terraformDestroy(INFRA_DIR, params.autoDestroy)
    }
}

def postScript() {
    email.sendEmail(
        "Destroy ${params.environment.toUpperCase()} SaintsXCTF CloudWatch Synthetic Monitoring Infrastructure",
        "",
        env.JOB_NAME,
        currentBuild.result,
        env.BUILD_NUMBER,
        env.BUILD_URL
    )

    cleanWs()
}