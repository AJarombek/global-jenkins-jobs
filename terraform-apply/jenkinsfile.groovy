/**
 * Jenkins script for applying changes to AWS infrastructure using Terraform
 * @author Andrew Jarombek
 * @since 11/21/2018
 */

node("master") {
    stage("checkout-terraform-scripts") {
        cleanWs()
        checkout([$class: 'GitSCM',
                  branches: [[name: '*/master']],
                  credentialsId: "865da7f9-6fc8-49f3-aa56-8febd149e72b",
                  userRemoteConfigs: [[url: "$repository_url"]]])
    }
    stage("apply-terraform") {
        dir("$terraform_directory") {
            sh "terraform --version"

            sh """
                # set +e
                terraform init
            """

            try {
                sh """
                    terraform plan
                    terraform apply -auto-approve
                """
            } catch (Exception ex) {
                echo "Terraform Plan/Apply Failed"
                currentBuild.result = "UNSTABLE"
            }
        }
    }
}