/**
 * Create a new freestyle job that builds other jobs.  This Jenkins job utilizes the DSL Plugin, allowing jobs to
 * be configured in code.  Full documentation found in https://github.com/AJarombek/devops-prototypes
 * @author Andrew Jarombek
 * @since 11/21/2018
 */

job("seed-jobs/seed-job") {
    description("Freestyle Job that builds other jobs")
    parameters {
        stringParam("repository", "", "Repository containing the Job DSL scripts")
        stringParam("branch", "", "Repo branch containing the Job DSL scripts")
    }
    scm {
        git {
            branch("\$branch")
            remote {
                credentials("ajarombek-github")
                github("AJarombek/\$repository", "ssh", "github.com")
            }
        }
    }
    steps {
        dsl {
            external("**/job_dsl.groovy")
        }
    }
}