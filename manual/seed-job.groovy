/**
 * Create a new freestyle job that builds other jobs.  This Jenkins job utilizes the DSL Plugin, allowing jobs to
 * be configured in code.  Full documentation found in https://github.com/AJarombek/devops-prototypes
 * @author Andrew Jarombek
 * @since 11/21/2018
 */

job("Seed_Job") {
    parameters {
        stringParam("job_dsl_repo", "", "Repository containing the Job DSL script")
        stringParam("job_dsl_branch", "", "Repo branch containing the Job DSL script")
        stringParam("job_dsl_path", "", "Location of Job DSL script")
    }
    scm {
        git {
            branch("\$job_dsl_branch")
            remote {
                credentials("865da7f9-6fc8-49f3-aa56-8febd149e72b")
                github("AJarombek/\$job_dsl_repo", "ssh", "github.com")
            }
        }
    }
    steps {
        dsl {
            external("\$job_dsl_path")
        }
    }
    wrappers {
        colorizeOutput()
    }
}