/**
 * Create a Pipeline Job for running unit, snapshot, and integration tests in the react-16-3-demo project.
 * @author Andrew Jarombek
 * @since 4/18/2020
 */

pipelineJob("prototypes/react-16-3-demo-test") {
    description("Pipeline Job for testing the react-16-3-demo project")
    parameters {
        stringParam('branch', 'master', 'Branch in the react-16-3-demo repository to test')
    }
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("prototypes/react-16-3-demo-test/Jenkinsfile.groovy"))
        }
    }
    triggers {
        cron('H 0 * * *')
    }
}