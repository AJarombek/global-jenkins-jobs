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
                  credentialsId: "ajarombek-github",
                  userRemoteConfigs: [[url: "git@github.com:AJarombek/kubernetes-prototype.git"]]])
    }
    if (is_create) {
        eksClusterStage(is_create)
        bastionKeyStage(is_create)
        eksNodesStage(is_create)
        bastionHostStage(is_create)
    } else {
        // When removing infrastructure, it should be destroyed in the reverse order of its creation
        bastionHostStage(is_create)
        eksNodesStage(is_create)
        bastionKeyStage(is_create)
        eksClusterStage(is_create)
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

/**
 * Pipeline stage that either creates or destroys the EKS Cluster
 * @param is_create If True, create the infrastructure.  If False, destroy the infrastructure.
 */
def eksClusterStage(boolean is_create) {
    stage("eks-cluster") {
        ansiColor('css') {
            tf(is_create, "infra/eks/cluster")
        }
    }
}

/**
 * Pipeline stage that either creates or destroys the SSH Key for the Bastion Host
 * @param is_create If True, create the infrastructure.  If False, destroy the infrastructure.
 */
def bastionKeyStage(boolean is_create) {
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
}

/**
 * Pipeline stage that either creates or destroys the EKS Cluster Nodes (EC2 Instances)
 * @param is_create If True, create the infrastructure.  If False, destroy the infrastructure.
 */
def eksNodesStage(boolean is_create) {
    stage("eks-nodes") {
        ansiColor('css') {
            tf(is_create, "infra/eks/nodes")
        }
    }
}

/**
 * Pipeline stage that either creates or destroys the EKS Cluster Bastion Host (EC2 Instance)
 * @param is_create If True, create the infrastructure.  If False, destroy the infrastructure.
 */
def bastionHostStage(boolean is_create) {
    stage("bastion-host") {
        ansiColor('css') {
            tf(is_create, "infra/bastion/host")
        }
    }
}