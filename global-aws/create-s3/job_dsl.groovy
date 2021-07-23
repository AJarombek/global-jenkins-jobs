/**
 * Create AWS S3 global jarombek assets.
 * @author Andrew Jarombek
 * @since 7/22/2021
 */

pipelineJob("global-aws/create-s3") {
    description("Pipeline Job for creating AWS S3 global jarombek assets.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("global-aws/create-s3/Jenkinsfile.groovy"))
        }
    }
    parameters {
        booleanParam('autoApply', true, "Whether the Terraform infrastructure should be automatically approved.")
    }
}
