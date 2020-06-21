/**
 * Create a Pipeline Job for running unit, snapshot, and integration tests in the saints-xctf-web project.
 * @author Andrew Jarombek
 * @since 3/14/2020
 */

pipelineJob("saints-xctf/web/saints-xctf-web-test") {
    description("Pipeline Job for testing the saints-xctf-web project")
    parameters {
        stringParam('branch', 'master', 'Branch in the saints-xctf-web repository to test')
    }
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/web/saints-xctf-web-test/Jenkinsfile.groovy"))
        }
    }
    triggers {
        cron('H 0 * * *')
    }
}