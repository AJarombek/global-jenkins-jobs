/**
 * Create a Pipeline Job for testing the global-aws-infrastructure-test project.  Runs python scripts to test AWS
 * infrastructure on a schedule.  Each module gets its own job.
 * @author Andrew Jarombek
 * @since 3/16/2019
 */

def environments = ["prod", "dev"]

environments.each { environment ->
    pipelineJob("global-aws/global-aws-infrastructure-test-${environment}") {
        description("Pipeline Job for testing the global-aws-infrastructure project")
        parameters {
            stringParam('branch', 'master', 'Branch in the global-aws-infrastructure repository to test')
        }
        environmentVariables {
            env('TEST_ENV', environment)
        }
        definition {
            cps {
                sandbox()
                script(readFileFromWorkspace("global-aws/global-aws-infrastructure-test/Jenkinsfile.groovy"))
            }
        }
    }
}