/**
 * Create AWS Lambda functions.
 * @author Andrew Jarombek
 * @since 7/19/2021
 */

pipelineJob("global-aws/create-lambda") {
    description("Pipeline Job for creating reusable AWS Lambda functions.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("global-aws/create-lambda/Jenkinsfile.groovy"))
        }
    }
    parameters {
        booleanParam('autoApply', true, "Whether the Terraform infrastructure should be automatically approved.")
    }
}
