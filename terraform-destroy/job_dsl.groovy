/**
 * Create a Pipeline Job for destroying AWS infrastructure using Terraform
 * @author Andrew Jarombek
 * @since 11/21/2018
 */

pipelineJob("terraform-destroy") {
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
                        github("AJarombek/global-jenkins-jobs", "ssh", "github.com")
                    }
                }
            }
            scriptPath("terraform-destroy/Jenkinsfile.groovy")
        }
    }
}