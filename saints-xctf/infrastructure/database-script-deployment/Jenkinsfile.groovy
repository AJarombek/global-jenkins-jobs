/**
 * Jenkins script that executes SQL scripts in the SaintsXCTF MySQL database.
 * @author Andrew Jarombek
 * @since 9/12/2020
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent agent {
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
        timestamps()
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
        stage("Push Script to S3") {
            steps {
                script {
                    pushScriptS3()
                }
            }
        }
        stage("Execute Deployment") {
            steps {
                script {
                    executeDeployment()
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
    genericsteps.checkoutRepo('saints-xctf-database', 'master')
}

def pushScriptS3() {
    def now = new Date()
    def formattedDate = now.format('yyyy-MM-dd', TimeZone.getTimeZone('UTC'))

    sh """
        export AWS_DEFAULT_REGION=us-east-1
        aws s3 $params.scriptPath s3://saints-xctf-database-deployments/$params.environment/$formattedDate/script.sql
    """
}

def executeDeployment() {
    sh """
        export AWS_DEFAULT_REGION=us-east-1
        aws lambda invoke \
            --function-name SaintsXCTFDatabaseDeployment${params.environment.toUpperCase()} \
            --payload '{ "file_path": "$params.environment/$formattedDate/script.sql" }' \
            response.json
    """
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