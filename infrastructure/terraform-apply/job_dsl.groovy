/**
 * Create a Pipeline Job for applying changes to AWS infrastructure using Terraform
 * @author Andrew Jarombek
 * @since 11/21/2018
 */

pipelineJob("infrastructure/terraform-apply") {
    description("Pipeline Job for applying changes to AWS infrastructure using Terraform")
    parameters {
        stringParam("repository_name", "", "Repository Name")
        stringParam("terraform_directory", "", "Terraform Directory")
    }
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("infrastructure/terraform-apply/Jenkinsfile.groovy"))
        }
    }
    logRotator {
        daysToKeep(10)
        numToKeep(5)
    }
}