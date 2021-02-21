/**
 * Destroy AWS CloudTrail trails and S3 buckets to hold CloudTrail logs.
 * @author Andrew Jarombek
 * @since 2/20/2021
 */

pipelineJob("global-aws/destroy-cloud-trail") {
    description("Pipeline Job for destroying AWS CloudTrail trails and S3 buckets to hold CloudTrail logs.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("global-aws/destroy-cloud-trail/Jenkinsfile.groovy"))
        }
    }
}
