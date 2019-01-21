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
                        credentials("865da7f9-6fc8-49f3-aa56-8febd149e72b")
                        github("AJarombek/global-jenkins-jobs", "ssh", "github.com")
                    }
                }
            }
            scriptPath("terraform-apply/jenkinsfile.groovy")
        }
    }
    wrappers {
        colorizeOutput()
    }
}