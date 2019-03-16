/**
 * Create a Pipeline Job for testing the saints-xctf-infrastructure project.  Runs python scripts to test AWS
 * infrastructure on a schedule.  Each environment gets its own job.
 * @author Andrew Jarombek
 * @since 3/16/2019
 */

def environments = ["prod", "dev"]

environments.each { env ->
    pipelineJob("saints-xctf-infrastructure-$env") {
        definition {
            cps {
                sandbox()
                script("saints-xctf-infrastructure/Jenkinsfile.groovy")
            }
        }
        triggers {
            cron('@daily')
        }
        wrappers {
            colorizeOutput()
        }
    }
}