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
    if (is_create) {
        eksClusterStage()
        bastionKeyStage()
        eksNodesStage()
        bastionHostStage()
    } else {
        // When removing infrastructure, it should be destroyed in the reverse order of its creation
        bastionHostStage()
        eksNodesStage()
        bastionKeyStage()
        eksClusterStage()
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
 */
def eksClusterStage() {
    stage("eks-cluster") {
        ansiColor('css') {
            tf(is_create, "infra/eks/cluster")
        }
    }
}

/**
 * Pipeline stage that either creates or destroys the SSH Key for the Bastion Host
 */
def bastionKeyStage() {
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
 */
def eksNodesStage() {
    stage("eks-nodes") {
        ansiColor('css') {
            tf(is_create, "infra/eks/nodes")
        }
    }
}

/**
 * Pipeline stage that either creates or destroys the EKS Cluster Bastion Host (EC2 Instance)
 */
def bastionHostStage() {
    stage("bastion-host") {
        ansiColor('css') {
            tf(is_create, "infra/bastion/host")
        }
    }
}