/**
 * Create a new freestyle job that builds other jobs.  This Jenkins job utilizes the DSL Plugin, allowing jobs to
 * be configured in code.  Full documentation found in https://github.com/AJarombek/devops-prototypes
 * @author Andrew Jarombek
 * @since 11/21/2018
 */

job("Seed_Job") {
    parameters {
        stringParam("job_dsl_repo", "", "Job DSL Repo")
        stringParam("job_dsl_branch", "", "Job DSL Branch")
        stringParam("job_dsl_path", "", "Location of Job DSL Groovy Script")
    }
    scm {
        git {
            branch("\$job_dsl_branch")
            remote {
                name("origin")
                url("\$job_dsl_repo")
            }
        }
    }
    steps {
        dsl {
            external("\$job_dsl_path")
        }
    }
}