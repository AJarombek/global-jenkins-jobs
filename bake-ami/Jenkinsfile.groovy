#!/usr/bin/groovy

@Library(['global-jenkins-library@master']) _

/**
 * Jenkins script for baking AMIs for the Jenkins server EC2 instance
 * @author Andrew Jarombek
 * @since 1/24/2019
 */

node("master") {
    stage("checkout-scripts") {
        cleanWs()

        // Perform a sparse checkout
        checkout([$class: 'GitSCM',
            branches: [[name: '*/branchName']],
            doGenerateSubmoduleConfigurations: false,
            extensions: [
                [$class: 'SparseCheckoutPaths',
                 sparseCheckoutPaths: [[$class: 'SparseCheckoutPath', path: 'jenkins/ami']]]
            ],
            submoduleCfg: [],
            userRemoteConfigs: [[
                credentialsId: '865da7f9-6fc8-49f3-aa56-8febd149e72b',
                url: 'git@github.com:AJarombek/global-aws-infrastructure.git'
            ]]
        ])
    }
    stage("bake-ami") {
        dir('jenkins/ami') {

            try {
                ansiColor('css') {
                    sh "packer --version"

                    sh """
                        packer validate jenkins-image.json
                        packer build jenkins-image.json
                    """
                }
            } catch (Exception ex) {
                echo "Packer Plan/Apply Failed"
                currentBuild.result = "UNSTABLE"
            }
        }
    }
}