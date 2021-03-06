/**
 * Create a Pipeline Job for testing the saints-xctf-infrastructure project.  Runs python scripts to test AWS
 * infrastructure on a schedule.  Each environment gets its own job.
 * @author Andrew Jarombek
 * @since 3/16/2019
 */

def environments = ["prod", "dev"]

environments.each { env ->
    pipelineJob("saints-xctf/infrastructure/saints-xctf-infrastructure-test-$env") {
        description("Pipeline Job for testing the saints-xctf-infrastructure project")
        parameters {
            stringParam('branch', 'master', 'Branch in the saints-xctf-infrastructure repository to test')
        }
        definition {
            cps {
                sandbox()
                script(
                    readFileFromWorkspace(
                        "saints-xctf/infrastructure/saints-xctf-infrastructure-test/Jenkinsfile.groovy"
                    )
                )
            }
        }
        triggers {
            cron('H 0 * * *')
        }
        logRotator {
            daysToKeep(10)
            numToKeep(5)
        }
    }
}