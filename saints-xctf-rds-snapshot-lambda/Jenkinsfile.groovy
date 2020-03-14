#!/usr/bin/groovy

@Library(['global-jenkins-library@master']) _

/**
 * Jenkins script for deploying the SaintsXCTF RDS snapshot (database backup) lambda function.
 * It places the database backup in an S3 bucket.
 * @author Andrew Jarombek
 * @since 6/7/2019
 */

final String PARAM_BRANCH = branch
final String PARAM_ENV = env
final String REPO = "git@github.com:AJarombek/saints-xctf-infrastructure.git"

node("master") {
    stage("checkout") {
        cleanWs()
        checkout([$class: 'GitSCM',
                  branches: [[name: "*/$PARAM_BRANCH"]],
                  credentialsId: "ajarombek-github",
                  userRemoteConfigs: [[url: REPO]]])
    }
    stage("zip lambda") {
        dir('database-snapshot/module/lambda') {
            // TODO: Zip the function with dependencies
        }
    }
    stage("terraform") {
        terraform.terraformApply("database-snapshot/env/$PARAM_ENV", REPO, false)
    }
    stage("clean") {
        cleanWs()
    }
}