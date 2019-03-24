/**
 * Jenkins script for destroying AWS infrastructure using Terraform
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
            ansiColor('xterm') {
                sh "terraform --version"

                sh """
                    # set +e
                    terraform init
                """
            }

            try {
                ansiColor('xterm') {
                    sh """
                        terraform destroy -auto-approve
                    """
                }
            } catch (Exception ex) {
                echo "Terraform Destroy Failed"
                currentBuild.result = "UNSTABLE"
            }
        }
    }
}