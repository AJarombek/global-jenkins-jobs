/**
 * Create a Pipeline Job for running unit, snapshot, and integration tests in the jarombek-react-components project.
 * @author Andrew Jarombek
 * @since 3/23/2020
 */

pipelineJob("jarombek-react-components/jarombek-react-components-test") {
    description("Pipeline Job for testing the jarombek-react-components project")
    parameters {
        stringParam('branch', 'master', 'Branch in the jarombek-react-components repository to test')
    }
    concurrentBuild(true)
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("jarombek-react-components-test/Jenkinsfile.groovy"))
        }
    }
}