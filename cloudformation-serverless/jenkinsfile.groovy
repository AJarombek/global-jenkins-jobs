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
                  credentialsId: "865da7f9-6fc8-49f3-aa56-8febd149e72b",
                  userRemoteConfigs: [[url: git_url]]])
    }
    stage("build-cloudformation-template") {
        sh "awscli --version"
    }
}