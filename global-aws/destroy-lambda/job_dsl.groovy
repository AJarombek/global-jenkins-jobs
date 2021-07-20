/**
 * Destroy AWS Lambda functions.
 * @author Andrew Jarombek
 * @since 7/19/2021
 */

pipelineJob("global-aws/destroy-lambda") {
    description("Pipeline Job for destroying reusable AWS Lambda functions.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("global-aws/destroy-lambda/Jenkinsfile.groovy"))
        }
    }
    parameters {
        booleanParam('autoDestroy', true, "Whether the Terraform infrastructure should be automatically destroyed.")
    }
}
