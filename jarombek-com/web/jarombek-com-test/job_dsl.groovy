/**
 * Create a Pipeline Job for testing the jarombek-com project.
 * Runs Unit tests for the React client and Node.js server.
 * @author Andrew Jarombek
 * @since 5/12/2019
 */

pipelineJob("jarombek-com/web/jarombek-com-test") {
    description("Pipeline Job for testing the jarombek-com project")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("jarombek-com/web/jarombek-com-test/Jenkinsfile.groovy"))
        }
    }
    logRotator {
        daysToKeep(10)
        numToKeep(5)
    }
}
