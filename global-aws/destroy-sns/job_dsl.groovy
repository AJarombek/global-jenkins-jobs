/**
 * Destroy global AWS SNS topics and subscriptions.
 * @author Andrew Jarombek
 * @since 7/15/2021
 */

pipelineJob("global-aws/destroy-sns") {
    description("Pipeline Job for destroying global AWS SNS topics and subscriptions..")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("global-aws/destroy-sns/Jenkinsfile.groovy"))
        }
    }
    parameters {
        booleanParam('autoDestroy', true, "Whether the Terraform infrastructure should be automatically destroyed.")
    }
}
