/**
 * Jenkins script that schedules start and stop times for the SaintsXCTF development MySQL database.
 * @author Andrew Jarombek
 * @since 9/16/2020
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        label 'master'
    }
    parameters {
        choice(
            name: 'action',
            choices: ['start', 'stop'],
            description: 'Whether to start or stop the database.'
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
    triggers {
        parameterizedCron('''
            15 11 * * * %action=start
            45 1 * * * %action=stop
        ''')
    }
    stages {
        stage("Clean Workspace") {
            steps {
                script {
                    cleanWs()
                }
            }
        }
        stage("Schedule Database") {
            steps {
                script {
                    sh 'export AWS_DEFAULT_REGION=us-east-1'

                    if (params.action == 'stop') {
                        sh 'aws rds stop-db-instance --db-instance-identifier saints-xctf-mysql-database-dev'
                    } else {
                        sh 'aws rds start-db-instance --db-instance-identifier saints-xctf-mysql-database-dev'
                    }
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
    def bodyTitle = "Schedule SaintsXCTF Development Database."
    def bodyContent = "Action: ${params.action.capitalize()}"
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}
