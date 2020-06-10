/**
 * Jenkins script for destroying AWS infrastructure using CloudFormation
 * for the python serverless backend prototype
 * @author Andrew Jarombek
 * @since 11/27/2018
 */

node("master") {
    stage("checkout-cloudformation-scripts") {
        cleanWs()

        def git_url = "git@github.com:AJarombek/python-serverless-backend-prototype.git"
        checkout([$class: 'GitSCM',
                  branches: [[name: '*/master']],
                  credentialsId: "ajarombek-github",
                  userRemoteConfigs: [[url: git_url]]])
    }
    stage("build-cloudformation-template") {
        sh "awscli --version"
    }
}