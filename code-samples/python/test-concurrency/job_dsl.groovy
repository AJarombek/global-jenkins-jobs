/**
 * Create a Pipeline Job for testing Python concurrency code samples.
 * @author Andrew Jarombek
 * @since 10/4/2020
 */

pipelineJob("code-samples/python/test-concurrency") {
    description("Pipeline Job for testing Python concurrency code samples")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("code-samples/python/test-concurrency/Jenkinsfile.groovy"))
        }
    }
}