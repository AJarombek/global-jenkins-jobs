/**
 * Jenkins script that executes SQL scripts in the SaintsXCTF MySQL database.
 * @author Andrew Jarombek
 * @since 9/12/2020
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        label 'master'
    }
    parameters {
        choice(
            name: 'environment',
            choices: ['dev', 'prod'],
            description: 'Environment to create the secrets.'
        )
        string(
            name: 'scriptPath',
            defaultValue: '',
            description: 'File path in the saints-xctf-database repository of a SQL script.'
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

def postScript() {
    def bodyTitle = "SaintsXCTF database script deployment."
    def bodyContent = "Deployed $params.scriptPath in the $params.environment environment."
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}