/**
 * Create AWS CloudTrail trails and S3 buckets to hold CloudTrail logs.
 * @author Andrew Jarombek
 * @since 2/20/2021
 */

pipelineJob("global-aws/create-cloud-trail") {
    description("Pipeline Job for creating AWS CloudTrail trails and S3 buckets to hold CloudTrail logs.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("global-aws/create-cloud-trail/Jenkinsfile.groovy"))
        }
    }
    parameters {
        booleanParam('autoApply', true, "Whether the Terraform infrastructure should be automatically approved.")
    }
}
