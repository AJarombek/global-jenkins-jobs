/**
 * Create a Pipeline Job for testing the global-aws-infrastructure project.  Runs python scripts to test AWS
 * infrastructure on a schedule.  Each module gets its own job.
 * @author Andrew Jarombek
 * @since 3/16/2019
 */

def modules = ["global", "jenkins", "sandbox"]

modules.each { module ->
    pipelineJob("global-aws-infrastructure-$module") {
        definition {
            cps {
                sandbox()
                script("global-aws-infrastructure/Jenkinsfile.groovy")
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
