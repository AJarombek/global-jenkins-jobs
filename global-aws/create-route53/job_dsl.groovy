/**
 * Create AWS Route53 records and health checks.
 * @author Andrew Jarombek
 * @since 2/20/2021
 */

pipelineJob("global-aws/create-route53") {
    description("Pipeline Job for creating AWS Route53 records and health checks.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("global-aws/create-route53/Jenkinsfile.groovy"))
        }
    }
}
