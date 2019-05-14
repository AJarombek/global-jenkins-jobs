/**
 * Create a Pipeline Job for destroying AWS infrastructure using Terraform
 * @author Andrew Jarombek
 * @since 11/21/2018
 */

pipelineJob("terraform-destroy") {
    description("Pipeline Job for destroying AWS infrastructure using Terraform")
    parameters {
        stringParam("repository_url", "", "Repository URL")
        stringParam("terraform_directory", "", "Terraform Directory")
    }
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("terraform-destroy/Jenkinsfile.groovy"))
        }
    }
}