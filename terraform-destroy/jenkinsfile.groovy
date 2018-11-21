/**
 * Jenkins script for destroying AWS infrastructure using Terraform
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
                    terraform destroy
                """
            } catch (Exception ex) {
                echo "Terraform Destroy Failed"
                currentBuild.result = "UNSTABLE"
            }
        }
    }
}