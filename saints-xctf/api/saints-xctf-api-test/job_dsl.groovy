/**
 * Create a Pipeline Job for running unit tests in the saints-xctf-api project.
 * @author Andrew Jarombek
 * @since 3/15/2020
 */

pipelineJob("saints-xctf/api/saints-xctf-api-test") {
    description("Pipeline Job for testing the saints-xctf-api project")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/api/saints-xctf-api-test/Jenkinsfile.groovy"))
        }
    }
}