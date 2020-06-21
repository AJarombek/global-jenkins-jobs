/**
 * Create a Pipeline Job for testing the jarombek-com-infrastructure project.  Runs python scripts to test AWS
 * infrastructure on a schedule.  Each environment gets its own job.
 * @author Andrew Jarombek
 * @since 6/2/2019
 */

def environments = ["prod", "dev"]

environments.each { env ->
    pipelineJob("jarombek-com/infrastructure/jarombek-com-infrastructure-$env") {
        description("Pipeline Job for testing the jarombek-com-infrastructure project")
        parameters {
            stringParam('branch', 'master', 'Branch in the jarombek-com-infrastructure repository to test')
        }
        definition {
            cps {
                sandbox()
                script(
                    readFileFromWorkspace(
                        "jarombek-com/infrastructure/jarombek-com-infrastructure-test/Jenkinsfile.groovy"
                    )
                )
            }
        }
    }
}