/**
 * Jenkins script for applying changes to AWS infrastructure using Terraform
 * @author Andrew Jarombek
 * @since 11/21/2018
 */

node("master") {
    stage("checkout-terraform-scripts") {
        checkout([$class: 'GitSCM', branches: [[name: '*/master']],
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
                    terraform apply
                """
            } catch (Exception ex) {
                echo "Terraform Plan/Apply Failed"
                currentBuild.result = "UNSTABLE"
            }
        }
    }
}