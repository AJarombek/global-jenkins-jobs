/**
 * Jenkins script that executes SQL scripts in the SaintsXCTF MySQL database.
 * @author Andrew Jarombek
 * @since 9/12/2020
 */

@Library(['global-jenkins-library@master']) _

def podYaml = '''
apiVersion: v1
kind: Pod
metadata:
  name: database-script-deployment
  namespace: jenkins
  labels:
    version: v1.0.0
    environment: development
    application: saints-xctf-database
spec:
  containers:
    - name: mysql-client
      image: widdpim/mysql-client:latest
      command: ["sleep", "infinity"]
      tty: true
'''

pipeline {
    agent none
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
            agent {
                label 'master'
            }
            steps {
                script {
                    cleanWs()
                }
            }
        }
        stage("Get Database Host") {
            agent {
                label 'master'
            }
            steps {
                script {
                    getHost()
                }
            }
        }
        stage("Execute Deployment") {
            agent {
                kubernetes {
                    yaml podYaml
                }
            }
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
def getHost() {
    HOST = sh(
        script: """
            export AWS_DEFAULT_REGION=us-east-1
            aws rds describe-db-instances \
                --db-instance-identifier saints-xctf-mysql-database-$params.environment \
                --query "DBInstances[0].Endpoint.Address" \
                --output text
        """,
        returnStdout: true
    )
}

def executeDeployment() {
    container('mysql-client') {
        genericsteps.checkoutRepo('saints-xctf-database', 'master')

        withCredentials([
            usernamePassword(
                credentialsId: "saintsxctf-rds-$params.environment",
                passwordVariable: 'password',
                usernameVariable: 'username'
            )
        ]) {
            dir('repos/saints-xctf-database') {
                sh """
                    export HOST=$HOST
                    export MYSQL_PWD="$password"
                    mysql -h \${HOST//[\$'\\t\\r\\n']} -u $username saintsxctf < $params.scriptPath
                """
            }
        }
    }
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