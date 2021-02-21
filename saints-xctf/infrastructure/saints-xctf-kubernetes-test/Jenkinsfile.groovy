/**
 * Jenkins script for testing the Kubernetes infrastructure defined in the saints-xctf-infrastructure repository.
 * @author Andrew Jarombek
 * @since 2/21/2021
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        kubernetes {
            yamlFile 'saints-xctf/infrastructure/saints-xctf-kubernetes-test/pod.yaml'
        }
    }
    triggers {
        cron('H 0 * * *')
    }
    options {
        ansiColor('xterm')
        timeout(time: 1, unit: 'HOURS')
        buildDiscarder(
            logRotator(daysToKeepStr: '10', numToKeepStr: '5')
        )
    }
    stages {
        stage("Clean Workspace") { steps { script { cleanWs() } } }
        stage("Checkout Repository") { steps { script { checkoutRepo() } } }
        stage("Execute Kubernetes Tests") { steps { script { executeTestScript() } } }
        stage("Cleanup Docker Environment") { steps { script { cleanupDockerEnvironment() } } }
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
    container('go') {
        dir('repos/saints-xctf-infrastructure') {
            git.basicClone('saints-xctf-infrastructure', 'master')
        }
    }
}

def executeTestScript() {
    container('go') {
        dir('repos/saints-xctf-infrastructure/test-k8s') {
            def testEnv = getEnv()
            sh """
                export TEST_ENV=$testEnv
                go test --incluster true -v
            """
        }
    }
}

def cleanupDockerEnvironment() {
    sh '''
        sudo docker system prune -f
    '''
}

def postScript() {
    def bodyTitle = "SaintsXCTF Kubernetes Infrastructure Tests"
    def bodyContent = ""
    def jobName = env.JOB_NAME
    def buildStatus = currentBuild.result
    def buildNumber = env.BUILD_NUMBER
    def buildUrl = env.BUILD_URL

    genericsteps.postScript(bodyTitle, bodyContent, jobName, buildStatus, buildNumber, buildUrl)
}

@NonCPS
def getEnv() {
    def matches = JOB_NAME =~ /saints-xctf-kubernetes-test-(\w+)/
    return matches[0][1]
}