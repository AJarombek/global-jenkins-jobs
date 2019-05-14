/**
 * Create a Pipeline Job for testing the global-aws-infrastructure project.  Runs python scripts to test AWS
 * infrastructure on a schedule.  Each module gets its own job.
 * @author Andrew Jarombek
 * @since 3/16/2019
 */

pipelineJob("global-aws-infrastructure") {
    description("Pipeline Job for testing the global-aws-infrastructure project")
    parameters {
        stringParam('branch', 'master', 'Branch in the global-aws-infrastructure repository to test')
    }
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("global-aws-infrastructure/Jenkinsfile.groovy"))
        }
    }
    triggers {
        cron('30 7 * * *')
    }
}
