#!/usr/bin/groovy

@Library(['global-jenkins-library@master']) _

/**
 * Jenkins script for applying changes to AWS infrastructure using Terraform
 * @author Andrew Jarombek
 * @since 11/21/2018
 */

final String PARAM_OPERATION = Operation
final boolean is_create = PARAM_OPERATION == 'create'

node("master") {
    stage("checkout") {
        cleanWs()
        checkout([$class: 'GitSCM',
                  branches: [[name: '*/master']],
                  credentialsId: "865da7f9-6fc8-49f3-aa56-8febd149e72b",
                  userRemoteConfigs: [[url: "git@github.com:AJarombek/kubernetes-prototype.git"]]])
    }
    stage("eks-cluster") {
        ansiColor('css') {
            tf(is_create, "infra/eks/cluster")
        }
    }
    stage("bastion-key") {
        dir("infra/bastion/key") {
            def result = sh(
                script: """
                    aws ec2 describe-key-pairs --key-name eks-sandbox-bastion-key --query "KeyPairs" | jq length
                """,
                returnStdout: true
            )
            if (result == "0") {
                ansiColor('css') {
                    tf(is_create, "infra/bastion/key")
                }
            } else {
                println "eks-sandbox-bastion-key already created."
            }
        }
    }
    stage("eks-nodes") {
        ansiColor('css') {
            tf(is_create, "infra/eks/nodes")
        }
    }
    stage("bastion-host") {
        ansiColor('css') {
            tf(is_create, "infra/bastion/host")
        }
    }
}

/**
 * Function to execute a Terraform apply or destroy
 * @param create If True, create the infrastructure.  If False, destroy the infrastructure.
 * @param path The directory path to the Terraform infrastructure which needs to be either created or deleted
 * @param repository Repo that the Terraform infrastructure exists in
 */
def tf(boolean create, String path, String repository = 'git@github.com:AJarombek/kubernetes-prototype.git') {
    if (create) {
        terraform.terraformApply(path, repository, true)
    } else {
        terraform.terraformDestroy(path, repository)
    }
}