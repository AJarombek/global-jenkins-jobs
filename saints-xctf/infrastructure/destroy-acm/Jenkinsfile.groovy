/**
 * Jenkins script that uses Terraform to destroy ACM certificates for SaintsXCTF.
 * @author Andrew Jarombek
 * @since 7/18/2020
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
            name: 'cert',
            choices: [
                '*.asset.saintsxctf.com',
                '*.auth.saintsxctf.com',
                '*.dev.saintsxctf.com',
                '*.fn.saintsxctf.com',
                '*.saintsxctf.com, saintsxctf.com',
                '*.uasset.saintsxctf.com'
            ],
            description: 'Certificate(s) to destroy.'
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
        stage("Terraform Plan") {
            steps {
                script {
                    terraformPlanDestroy()
                }
            }
        }
        stage("Terraform Destroy") {
            when {
                allOf {
                    environment name: 'TERRAFORM_NO_CHANGES', value: 'false'
                    environment name: 'TERRAFORM_PLAN_ERRORS', value: 'false'
                }
            }
            steps {
                script {
                    terraformDestroy()
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
    def name = "saints-xctf-infrastructure"
    def branch = "master"

    genericsteps.checkoutRepo(name, branch)
}

def terraformInit() {
    def certDirDict = [
        '*.asset.saintsxctf.com': 'asset-saints-xctf',
        '*.auth.saintsxctf.com': 'auth-saints-xctf',
        '*.dev.saintsxctf.com': 'dev-saints-xctf',
        '*.fn.saintsxctf.com': 'fn-saints-xctf',
        '*.saintsxctf.com, saintsxctf.com': 'saints-xctf',
        '*.uasset.saintsxctf.com': 'uasset-saints-xctf'
    ]

    INFRA_DIR = "repos/saints-xctf-infrastructure/acm/${certDirDict[params.cert]}"
    terraform.terraformInit(INFRA_DIR)
}

def terraformPlanDestroy() {
    terraform.terraformPlanDestroy(INFRA_DIR)
}

def terraformDestroy() {
    terraform.terraformDestroy(INFRA_DIR, params.autoDestroy)
}

def postScript() {
    def bodyTitle = "Destroy saints-xctf-infrastructure $param.cert ACM Infrastructure."
    def bodyContent = ""
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}
