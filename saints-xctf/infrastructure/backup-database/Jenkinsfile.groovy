/**
 * Jenkins script that backs up a database and places the backup SQL file in S3.
 * @author Andrew Jarombek
 * @since 7/19/2020
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
            description: 'Environment of the database.'
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
        stage("Backup Database") {
            steps {
                script {
                    sh "aws lambda invoke --function-name SaintsXCTFMySQLBackup${params.environment.toUpperCase()}"
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

def postScript() {
    def bodyTitle = "Backup SaintsXCTF $params.environment Database."
    def bodyContent = ""
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}
