/**
 * Create a Pipeline Job for applying changes to AWS infrastructure using Terraform
 * @author Andrew Jarombek
 * @since 11/21/2018
 */

pipelineJob("devops-jobs/terraform-apply") {
    description("Pipeline Job for applying changes to AWS infrastructure using Terraform")
    parameters {
        stringParam("repository_name", "", "Repository Name")
        stringParam("terraform_directory", "", "Terraform Directory")
    }
    concurrentBuild(true)
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("terraform-apply/Jenkinsfile.groovy"))
        }
    }
}