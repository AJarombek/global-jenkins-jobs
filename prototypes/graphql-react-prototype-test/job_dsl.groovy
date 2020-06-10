/**
 * Create a Pipeline Job for running unit, snapshot, and integration tests in the graphql-react-prototype project.
 * @author Andrew Jarombek
 * @since 4/18/2020
 */

pipelineJob("prototypes/graphql-react-prototype-test") {
    description("Pipeline Job for testing the graphql-react-prototype project")
    parameters {
        stringParam('branch', 'master', 'Branch in the graphql-react-prototype repository to test')
    }
    concurrentBuild(true)
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("prototypes/graphql-react-prototype-test/Jenkinsfile.groovy"))
        }
    }
    triggers {
        cron('H 0 * * *')
    }
}