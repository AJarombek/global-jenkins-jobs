/**
 * Destroy reusable AWS Lambda layers.
 * @author Andrew Jarombek
 * @since 1/30/2021
 */

pipelineJob("global-aws/destroy-lambda-layer") {
    description("Pipeline Job for destroying reusable AWS Lambda layers.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("global-aws/destroy-lambda-layer/Jenkinsfile.groovy"))
        }
    }
    parameters {
        booleanParam('autoDestroy', true, "Whether the Terraform infrastructure should be automatically destroyed.")
    }
}
