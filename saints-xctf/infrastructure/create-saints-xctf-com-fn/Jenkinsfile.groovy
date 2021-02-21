/**
 * Jenkins script that creates AWS infrastructure for fn.saintsxctf.com.
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
            name: 'autoApply',
            defaultValue: true,
            description: "Whether the Terraform infrastructure should be automatically approved."
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
        stage("Checkout Repositories") { steps { script { checkoutRepos() } } }
        stage("Get Lambda Zip Files") { steps { script { createLambdaZipFiles() } } }
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
    genericsteps.checkoutRepo('saints-xctf-infrastructure', 'master')
    genericsteps.checkoutRepo('saints-xctf-functions', 'main')
}

def createLambdaZipFiles() {
    dir('repos/saints-xctf-functions/forgot-password') {
        sh """
            yarn install --production=true
            zip -r9 SaintsXCTFForgotPasswordEmail.zip .
            cp SaintsXCTFForgotPasswordEmail.zip ../../saints-xctf-infrastructure/saints-xctf-com-fn/modules/email-lambda
        """
    }

    dir('repos/saints-xctf-functions/send-activation') {
        sh """
            yarn install --production=true
            zip -r9 SaintsXCTFActivationCodeEmail.zip .
            cp SaintsXCTFActivationCodeEmail.zip ../../saints-xctf-infrastructure/saints-xctf-com-fn/modules/email-lambda
        """
    }

    dir('repos/saints-xctf-functions/welcome') {
        sh """
            yarn install --production=true
            zip -r9 SaintsXCTFWelcomeEmail.zip .
            cp SaintsXCTFWelcomeEmail.zip ../../saints-xctf-infrastructure/saints-xctf-com-fn/modules/email-lambda
        """
    }

    dir('repos/saints-xctf-functions/upload-profile-picture') {
        sh """
            yarn install --production=true
            zip -r9 SaintsXCTFUassetUser.zip .
            cp SaintsXCTFUassetUser.zip ../../saints-xctf-infrastructure/saints-xctf-com-fn/modules/uasset-lambda
        """
    }

    dir('repos/saints-xctf-functions/upload-group-picture') {
        sh """
            yarn install --production=true
            zip -r9 SaintsXCTFUassetGroup.zip .
            cp SaintsXCTFUassetGroup.zip ../../saints-xctf-infrastructure/saints-xctf-com-fn/modules/uasset-lambda
        """
    }
}

def terraformInit() {
    INFRA_DIR = "repos/saints-xctf-infrastructure/saints-xctf-com-fn/env/$params.environment"
    terraform.terraformInit(INFRA_DIR)
}

def terraformValidate() {
    terraform.terraformValidate(INFRA_DIR)
}

def terraformPlan() {
    terraform.terraformPlan(INFRA_DIR)
}

def terraformApply() {
    terraform.terraformApply(INFRA_DIR, params.autoApply)
}

def postScript() {
    email.sendEmail(
        "Create ${params.environment.toUpperCase()} SaintsXCTF Functions AWS Infrastructure",
        "",
        env.JOB_NAME,
        currentBuild.result,
        env.BUILD_NUMBER,
        env.BUILD_URL
    )

    cleanWs()
}