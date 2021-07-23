/**
 * Destroy AWS S3 global jarombek assets.
 * @author Andrew Jarombek
 * @since 7/22/2021
 */

pipelineJob("global-aws/destroy-s3") {
    description("Pipeline Job for destroying reusable AWS S3 global jarombek assets.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("global-aws/destroy-s3/Jenkinsfile.groovy"))
        }
    }
    parameters {
        booleanParam('autoDestroy', true, "Whether the Terraform infrastructure should be automatically destroyed.")
    }
}
