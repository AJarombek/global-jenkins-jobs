/**
 * Jenkins pipeline for building and deploying the react-webpack-seed project.
 * @author Andrew Jarombek
 * @since 7/6/2018
 */

pipeline {
    agent master
    stages {
        stage('Checkout Repository') {
            cleanWs()
            checkout([$class: 'GitSCM',
                      branches: [[name: '*/master']],
                      credentialsId: "ajarombek-github",
                      userRemoteConfigs: [[url: "git@github.com:AJarombek/react-webpack-seed.git"]]])
        }
        stage('Install Dependencies') {
            steps {
                dir('react-webpack-seed') {
                    sh "echo In Install Dependencies Stage for build ${env.BUILD_ID}"
                    sh '''
                        set -x
                        npm install yarn -g
                        yarn
                        set +x
                    '''
                }
            }
        }
        stage('Test') {
            steps {
                dir('react-webpack-seed') {
                    sh '''
                        echo In Test Stage
                        set -x
                        yarn run test
                        set +x
                    '''
                }
            }
        }
        stage('Compile and Zip') {
            steps {
                dir('react-webpack-seed') {
                    sh '''
                        echo In Compile and Zip Stage
                        set -x
                        yarn run build
                        set +x
                    '''
                }
                dir('react-webpack-seed/dist') {
                    sh 'zip -r react_webpack.zip assets'
                }
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