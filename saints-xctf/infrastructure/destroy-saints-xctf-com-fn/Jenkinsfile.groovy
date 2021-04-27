/**
 * Jenkins script for destroying AWS infrastructure for fn.saintsxctf.com.
 * @author Andrew Jarombek
 * @since 11/21/2020
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
        buildDiscarder(logRotator(daysToKeepStr: '10', numToKeepStr: '5'))
    }
    stages {
        stage("Clean Workspace") { steps { script { cleanWs() } } }
        stage("Checkout Repository") { steps { script { checkoutRepo() } } }
        stage("Create ZIP Files") { steps { script { mockZipFiles() } } }
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
    genericsteps.checkoutRepo("saints-xctf-infrastructure", "master")
}

def mockZipFiles() {
    dir('repos/saints-xctf-infrastructure/saints-xctf-com-fn/modules/uasset-lambda') {
        sh '''
            touch SaintsXCTFUassetUser.zip
            touch SaintsXCTFUassetGroup.zip
        '''
    }

    dir('repos/saints-xctf-infrastructure/saints-xctf-com-fn/modules/email-lambda') {
        sh '''
            touch SaintsXCTFActivationCodeEmail.zip
            touch SaintsXCTFForgotPasswordEmail.zip
            touch SaintsXCTFReportEmail.zip
            touch SaintsXCTFWelcomeEmail.zip
        '''
    }
}

def terraformInit() {
    INFRA_DIR = "repos/saints-xctf-infrastructure/saints-xctf-com-fn/env/$params.environment"
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
        "Destroy ${params.environment.toUpperCase()} SaintsXCTF Function AWS Infrastructure",
        "",
        env.JOB_NAME,
        currentBuild.result,
        env.BUILD_NUMBER,
        env.BUILD_URL
    )

    cleanWs()
}