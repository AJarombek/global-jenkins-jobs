/**
 * Create AWS SNS topics and subscriptions.
 * @author Andrew Jarombek
 * @since 7/15/2021
 */

pipelineJob("global-aws/create-sns") {
    description("Pipeline Job for creating AWS SNS topics and subscriptions.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("global-aws/create-sns/Jenkinsfile.groovy"))
        }
    }
    parameters {
        booleanParam('autoApply', true, "Whether the Terraform infrastructure should be automatically approved.")
        stringParam('phoneNumber', '', 'Phone number to send SMS alerts to from an SNS topic.')
    }
}
