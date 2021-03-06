/**
 * Create a Pipeline Job for testing the kubernetes-prototype project.
 * Runs Unit tests for the React client and Node.js server.  Also tests the AWS infrastructure.
 * @author Andrew Jarombek
 * @since 5/12/2019
 */

pipelineJob("prototypes/kubernetes-prototype-ci") {
    description("Pipeline Job for testing the kubernetes-prototype project")
    parameters {
        stringParam('branch', 'master', 'Branch in the kubernetes-prototype repository to test')
    }
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("prototypes/kubernetes-prototype-ci/Jenkinsfile.groovy"))
        }
    }
    logRotator {
        daysToKeep(10)
        numToKeep(5)
    }
}
