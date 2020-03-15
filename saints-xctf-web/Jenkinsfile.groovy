/**
 * Jenkins script for testing saints-xctf-web.
 * @author Andrew Jarombek
 * @since 3/14/2020
 */

final String PARAM_BRANCH = branch

pipeline {
    agent {
        label 'master'
    }
    triggers {
        cron('H 0 * * *')
    }
    options {
        ansiColor('xterm')
        timeout(time: 1, unit: 'HOURS')
        buildDiscarder(
            logRotator(daysToKeep: '10', numToKeepStr: '5')
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
                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: "*/$PARAM_BRANCH"]],
                        credentialsId: "ajarombek-github",
                        userRemoteConfigs: [[url: "git@github.com:AJarombek/saints-xctf-web.git"]]
                    ])
                }
            }
        }
    }
}