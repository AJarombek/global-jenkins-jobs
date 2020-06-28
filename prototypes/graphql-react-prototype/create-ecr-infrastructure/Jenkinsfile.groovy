/**
 * Jenkins script that pushes a 'token' AWS Lambda function to ECR.
 * @author Andrew Jarombek
 * @since 6/28/2020
 */

@Library(['global-jenkins-library@master']) _

def INFRA_DIR

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
            steps {
                script {
                    terraformApply(params.autoApply)
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

// Stage functions
def checkoutRepo() {
    def name = "graphql-react-prototype"
    def branch = "master"

    genericsteps.checkoutRepo(name, branch)
}

def terraformInit() {
    INFRA_DIR = "repos/graphql-react-prototype/infra"
    dir(INFRA_DIR) {
        sh """
            terraform --version
            terraform init
        """
    }
}

def terraformValidate() {
    dir(INFRA_DIR) {
        sh "terraform validate"
    }
}

def terraformPlan() {
    dir(INFRA_DIR) {
        def result = sh(
            script: 'terraform plan -detailed-exitcode -out=terraform-dev.tfplan',
            returnStatus: true
        )

        // The result is 0 if the plan found no changes, 1 if there are errors with the plan,
        // and 2 if the plan is successful and changes will be made.
        switch (result) {
            case 0:
                currentBuild.result = 'SUCCESS'
                break
            case 1:
                currentBuild.result = 'UNSTABLE'
                break
            case 2:
                println 'The "terraform plan" Response Was Valid.'
                break
            default:
                println 'Unexpected Terraform exit code.'
                currentBuild.result = 'FAILURE'
        }
    }
}

def terraformApply(autoApply) {
    if (!autoApply) {
        try {
            timeout(time: 15, unit: 'MINUTES') {
                input message: 'Confirm Plan', ok: 'Apply'
            }
        } catch (Throwable ex) {
            println 'Timeout Exceeded.'
            currentBuild.result = 'UNSTABLE'
        }
    }
    dir(INFRA_DIR) {
        sh "terraform apply -auto-approve terraform-dev.tfplan"
    }
}

def postScript() {
    def bodyTitle = "Create graphql-react-prototype ECR Infrastructure."
    def bodyContent = ""
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}
