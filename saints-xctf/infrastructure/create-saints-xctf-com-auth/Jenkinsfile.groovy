/**
 * Jenkins script for creating AWS infrastructure for auth.saintsxctf.com.
 * @author Andrew Jarombek
 * @since 6/19/2020
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        kubernetes {
            yaml '''
apiVersion: v1
kind: Pod
metadata:
  name: saints-xctf-auth
  namespace: jenkins
  labels:
    version: v1.0.0
    environment: development
    application: saints-xctf-auth
spec:
  containers:
    - name: authenticate
      image: ajarombek/auth-saints-xctf-com-authenticate:latest
      tty: true
    - name: authorizer
      image: ajarombek/auth-saints-xctf-com-authorizer:latest
      tty: true
    - name: rotate
      image: ajarombek/auth-saints-xctf-com-rotate:latest
      tty: true
    - name: token
      image: ajarombek/auth-saints-xctf-com-token:latest
      tty: true
    - name: terraform
      image: hashicorp/terraform:latest
      command: ["sleep", "infinity"]
      tty: true
            '''
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
        stage("Checkout Repositories") {
            steps {
                script {
                    checkoutRepos()
                }
            }
        }
        stage("Get Lambda Zip Files") {
            steps {
                script {
                    getLambdaZipFiles()
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
        stage("Terraform Validate") {
            steps {
                script {
                    terraformValidate()
                }
            }
        }
        stage("Terraform Plan") {
            steps {
                script {
                    terraformPlan()
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

def checkoutRepos() {
    container('terraform') {
        genericsteps.checkoutRepo('saints-xctf-infrastructure', 'master')
        genericsteps.checkoutRepo('saints-xctf-auth', 'master')
    }
}

def getLambdaZipFiles() {
    container('authenticate') {
        sh 'cp /dist/SaintsXCTFAuthenticate.zip $(pwd)'
        stash name: "authenticateZip", includes: 'SaintsXCTFAuthenticate.zip'
    }

    container('authorizer') {
        sh 'cp /dist/SaintsXCTFAuthorizer.zip $(pwd)'
        stash name: "authorizerZip", includes: 'SaintsXCTFAuthorizer.zip'
    }

    container('rotate') {
        sh 'cp /dist/SaintsXCTFRotate.zip $(pwd)'
        stash name: "rotateZip", includes: 'SaintsXCTFRotate.zip'
    }

    container('token') {
        sh 'cp /dist/SaintsXCTFToken.zip $(pwd)'
        stash name: "tokenZip", includes: 'SaintsXCTFToken.zip'
    }

    container('terraform') {
        dir("repos/saints-xctf-infrastructure/saints-xctf-com-auth/modules/lambda") {
            unstash name: "authenticateZip"
            unstash name: "authorizerZip"
            unstash name: "rotateZip"
            unstash name: "tokenZip"
            sh "ls -ltr"
        }
    }
}

def terraformInit() {
    INFRA_DIR = "repos/saints-xctf-infrastructure/saints-xctf-com-auth/env/$params.environment"

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
        "Create SaintsXCTF Auth AWS Infrastructure",
        "",
        env.JOB_NAME,
        currentBuild.result,
        env.BUILD_NUMBER,
        env.BUILD_URL
    )

    cleanWs()
}