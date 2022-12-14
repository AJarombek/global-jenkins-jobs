/**
 * Create a Pipeline Job for testing the jarombek-com-infrastructure project.  Runs python scripts to test AWS
 * infrastructure on a schedule.  Each environment gets its own job.
 * @author Andrew Jarombek
 * @since 6/2/2019
 */

def environments = ["prod", "dev"]

environments.each { environment ->
    pipelineJob("jarombek-com/infrastructure/jarombek-com-aws-infrastructure-test-$environment") {
        description("Pipeline Job for testing AWS infrastructure in the jarombek-com-infrastructure project")
        environmentVariables {
            env('TEST_ENV', environment)
        }
        definition {
            cps {
                sandbox()
                script(
                    readFileFromWorkspace(
                        "jarombek-com/infrastructure/jarombek-com-aws-infrastructure-test/Jenkinsfile.groovy"
                    )
                )
            }
        }
    }
}