/**
 * Create reusable AWS Lambda layers.
 * @author Andrew Jarombek
 * @since 1/30/2021
 */

pipelineJob("global-aws/create-lambda-layer") {
    description("Pipeline Job for creating reusable AWS Lambda layers.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("global-aws/create-lambda-layer/Jenkinsfile.groovy"))
        }
    }
    parameters {
        booleanParam('autoApply', true, "Whether the Terraform infrastructure should be automatically approved.")
    }
}
