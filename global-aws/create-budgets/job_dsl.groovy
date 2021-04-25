/**
 * Create AWS account budgets and alerts.
 * @author Andrew Jarombek
 * @since 2/20/2021
 */

pipelineJob("global-aws/create-budgets") {
    description("Pipeline Job for creating AWS account budgets and alerts.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("global-aws/create-budgets/Jenkinsfile.groovy"))
        }
    }
    parameters {
        booleanParam('autoApply', true, "Whether the Terraform infrastructure should be automatically approved.")
    }
}
