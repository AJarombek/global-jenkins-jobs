/**
 * Create a Pipeline Job for testing the jarombek-com project.
 * Runs Unit tests for the React client and Node.js server.
 * @author Andrew Jarombek
 * @since 5/12/2019
 */

pipelineJob("jarombek-com") {
    parameters {
        stringParam('branch', 'master', 'Branch in the jarombek-com repository to test')
    }
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("jarombek-com/Jenkinsfile.groovy"))
        }
    }
}
