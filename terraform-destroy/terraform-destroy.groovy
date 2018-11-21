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
            external("/terraform-destroy/jenkinsfile.groovy")
        }
    }
}