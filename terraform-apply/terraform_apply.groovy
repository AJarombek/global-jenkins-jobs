/**
 * Create a Pipeline Job for applying changes to AWS infrastructure using Terraform
 * @author Andrew Jarombek
 * @since 11/21/2018
 */

pipelineJob("terraform-apply") {
    parameters {
        stringParam("repository_url", "", "Repository URL")
        stringParam("terraform_directory", "", "Terraform Directory")
    }
    definition {
        cpsScm {
            scm {
                git {
                    branch("master")
                    remote {
                        credentials("ajarombek-github-ssh")
                        github("AJarombek/global-jenkins-jobs.git")
                    }
                }
            }
            scriptPath("terraform-apply/jenkinsfile.groovy")
        }
    }
}