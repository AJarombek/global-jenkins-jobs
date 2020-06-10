/**
 * Create a Pipeline Job for destroying AWS infrastructure using Terraform
 * @author Andrew Jarombek
 * @since 11/21/2018
 */

pipelineJob("infrastructure/terraform-destroy") {
    description("Pipeline Job for destroying AWS infrastructure using Terraform")
    parameters {
        stringParam("repository_name", "", "Repository Name")
        stringParam("terraform_directory", "", "Terraform Directory")
    }
    concurrentBuild(true)
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("infrastructure/terraform-destroy/Jenkinsfile.groovy"))
        }
    }
    logRotator {
        daysToKeep(10)
        numToKeep(5)
    }
}