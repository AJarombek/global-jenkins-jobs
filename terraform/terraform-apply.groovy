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
    scm {
        git {
            branch("master")
            remote {
                name("origin")
                url("https://github.com/AJarombek/global-jenkins-jobs.git")
            }
        }
    }
    steps {
        dsl {
            external("/terraform/jenkinsfile.groovy")
        }
    }
}