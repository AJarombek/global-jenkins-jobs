/**
 * Jenkins script for applying changes to AWS infrastructure using Terraform
 * @author Andrew Jarombek
 * @since 11/21/2018
 */

PARAM_OPERATION = Operation
def create = PARAM_OPERATION == 'create'

node("master") {
    stage("checkout") {
        cleanWs()
        checkout([$class: 'GitSCM',
                  branches: [[name: '*/master']],
                  credentialsId: "865da7f9-6fc8-49f3-aa56-8febd149e72b",
                  userRemoteConfigs: [[url: "git@github.com:AJarombek/kubernetes-prototype.git"]]])
    }
    stage("eks-cluster") {
        dir("eks/cluster") {
            ansiColor('css') {
                terraform(create)
            }
        }
    }
    stage("bastion-key") {
        dir("bastion/key") {
            def result = sh(
                script: "aws ec2 describe-key-pairs --key-name eks-sandbox-bastion-key --query \"KeyPairs\" | jq length",
                returnStdout: true
            )
            if (result == "0") {
                ansiColor('css') {
                    terraform(create)
                }
            } else {
                println "eks-sandbox-bastion-key already created."
            }
        }
    }
    stage("eks-nodes") {
        dir("eks/nodes") {
            ansiColor('css') {
                terraform(create)
            }
        }
    }
    stage("bastion-host") {
        dir("bastion/host") {
            ansiColor('css') {
                terraform(create)
            }
        }
    }
}

/**
 * Function to execute a Terraform apply or destroy
 * @param create - If True, create the infrastructure.  If False, destroy the infrastructure.
 */
def terraform(boolean create) {
    if (create) {
        sh """
            terraform init
            terraform plan
            terraform apply -auto-approve
        """
    } else {
        sh "terraform destroy -auto-approve"
    }
}