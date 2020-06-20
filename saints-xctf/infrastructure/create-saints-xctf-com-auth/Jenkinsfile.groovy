/**
 * Jenkins script for creating AWS infrastructure for auth.saintsxctf.com.
 * @author Andrew Jarombek
 * @since 6/19/2020
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        label 'master'
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
        stage("Create Infrastructure") {
            steps {
                script {
                    createInfrastructure()
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

def checkoutRepo() {
    dir('repos/saints-xctf-infrastructure') {
        git.basicClone('saints-xctf-infrastructure', 'master')
    }
}

def createInfrastructure() {
    dir("repos/saints-xctf-infrastructure") {
        def status = sh (
            script: """
                echo 'TODO'
            """,
            returnStatus: true
        )

        if (status >= 1) {
            currentBuild.result = "UNSTABLE"
        } else {
            currentBuild.result = "SUCCESS"
        }
    }
}

def postScript() {
    email.sendEmail(
        "Create SaintsXCTF Auth AWS Infrastructure",
        "",
        env.JOB_NAME,
        currentBuild.result,
        env.BUILD_NUMBER,
        env.BUILD_URL
    )

    cleanWs()
}