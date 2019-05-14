/**
 * Create a new freestyle job that builds a single other job
 * @author Andrew Jarombek
 * @since 3/15/2019
 */

job("single-seed-job") {
    description("Freestyle Job that builds a single other job")
    parameters {
        stringParam("repository", "", "Repository containing the Job DSL script")
        stringParam("branch", "", "Repo branch containing the Job DSL script")
        stringParam("job_dsl_path", "", "Location of Job DSL script")
    }
    scm {
        git {
            branch("\$branch")
            remote {
                credentials("865da7f9-6fc8-49f3-aa56-8febd149e72b")
                github("AJarombek/\$repository", "ssh", "github.com")
            }
        }
    }
    steps {
        dsl {
            external("\$job_dsl_path")
        }
    }
}