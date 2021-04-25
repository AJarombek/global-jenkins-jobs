/**
 * Destroy global AWS Route53 records and health checks.
 * @author Andrew Jarombek
 * @since 2/20/2021
 */

pipelineJob("global-aws/destroy-budgets") {
    description("Pipeline Job for destroying global AWS Route53 records and health checks..")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("global-aws/destroy-route53/Jenkinsfile.groovy"))
        }
    }
    parameters {
        booleanParam('autoDestroy', true, "Whether the Terraform infrastructure should be automatically destroyed.")
    }
}
