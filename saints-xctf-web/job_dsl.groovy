/**
 * Create a Pipeline Job for running unit, snapshot, and integration tests in the saints-xctf-web project.
 * @author Andrew Jarombek
 * @since 3/14/2020
 */

pipelineJob("saints-xctf-web/saints-xctf-web") {
    description("Pipeline Job for testing the saints-xctf-web project")
    parameters {
        stringParam('branch', 'master', 'Branch in the saints-xctf-web repository to test')
    }
    concurrentBuild(true)
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf-web/Jenkinsfile.groovy"))
        }
    }
    triggers {
        cron('H 0 * * *')
    }
}