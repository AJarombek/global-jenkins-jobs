/**
 * Jenkins pipeline for building and deploying the react-webpack-seed project.
 * @author Andrew Jarombek
 * @since 7/6/2018
 */

pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh "echo In Build Stage for build ${env.BUILD_ID}"
                sh '''
                    set -x
                    npm install yarn -g
                    yarn
                    set +x
                '''
            }
        }
        stage('Test') {
            steps {
                sh '''
                    echo In Test Stage
                    set -x
                    yarn run test
                    set +x
                '''
            }
        }
        stage('Deploy') {
            steps {
                sh '''
                    echo In Deploy Stage
                    set -x
                    yarn run start
                    set +x
                '''
            }
        }
    }
    post {
        // To get emails working, Gmail settings must be changes to allow for less secure apps
        // You must test emails first from 'Manage Jenkins' -> 'Configure System'
        failure {
            mail to: 'andrew@jarombek.com',
                    subject: 'Jenkins Pipeline Failed for react-webpack-seed',
                    body: 'Something went wrong with build #${env.BUILD_ID}!'
        }
        success {
            mail to: 'andrew@jarombek.com',
                    subject: 'Jenkins Pipeline Successful for react-webpack-seed!',
                    body: "Success for build #${env.BUILD_ID}!"
        }
    }
}