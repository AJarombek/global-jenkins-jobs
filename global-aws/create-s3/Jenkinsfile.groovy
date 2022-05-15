/**
 * Jenkins script that uses Terraform to create global AWS S3 global jarombek assets.
 * @author Andrew Jarombek
 * @since 7/22/2021
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        kubernetes {
            yaml """\
                apiVersion: v1
                kind: Pod
                metadata:
                  name: global-aws-create-s3
                  namespace: jenkins
                  labels:
                    version: v1.0.0
                    environment: development
                    application: global-aws-create-s3
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

// Stage functions
def checkoutRepo() {
    def name = "global-aws-infrastructure"
    def branch = "master"

    genericsteps.checkoutRepo(name, branch)
}

def terraformInit() {
    INFRA_DIR = "repos/global-aws-infrastructure/s3"

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
    def bodyTitle = "Create global-aws-infrastructure S3 global jarombek assets."
    def bodyContent = ""
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}
