/**
 * Jenkins script that uses Terraform to create ECR repositories used by the GraphQL React Prototype.
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

// Stage functions
def checkoutRepo() {
    def name = "graphql-react-prototype"
    def branch = "master"

    genericsteps.checkoutRepo(name, branch)
}

def terraformInit() {
    INFRA_DIR = "repos/graphql-react-prototype/infra/ecr"
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
    def bodyTitle = "Create graphql-react-prototype ECR Infrastructure."
    def bodyContent = ""
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}
