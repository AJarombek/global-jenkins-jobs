/**
 * Jenkins script that restores a database from a backup.
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
            choices: ['dev'],
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
        stage("Restore Database") {
            steps {
                script {
                    sh """
                        export AWS_DEFAULT_REGION=us-east-1
                        aws lambda invoke --function-name SaintsXCTFMySQLRestore${params.environment.toUpperCase()} response.json
                        cat response.json
                    """
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
    def bodyTitle = "Restore SaintsXCTF $params.environment Database."
    def bodyContent = ""
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}
