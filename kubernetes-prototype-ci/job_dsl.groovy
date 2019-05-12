/**
 * Create a Pipeline Job for testing the kubernetes-prototype project.
 * Runs Unit tests for the React client and Node.js server.  Also tests the AWS infrastructure.
 * @author Andrew Jarombek
 * @since 5/12/2019
 */

pipelineJob("kubernetes-prototype-ci") {
    parameters {
        stringParam('branch', 'master', 'Branch in the kubernetes-prototype repository to test')
    }
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("kubernetes-prototype/Jenkinsfile.groovy"))
        }
    }
}
