/**
 * Create a Pipeline Job for detecting unexpected AWS costs.
 * @author Andrew Jarombek
 * @since 7/4/2020
 */

pipelineJob("global-aws/cost-detection") {
    description("Pipeline Job for detecting unexpected AWS costs.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("global-aws/cost-detection/Jenkinsfile.groovy"))
        }
    }
}
