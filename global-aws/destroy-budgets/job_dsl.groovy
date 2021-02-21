/**
 * Destroy AWS account budgets and alerts.
 * @author Andrew Jarombek
 * @since 2/20/2021
 */

pipelineJob("global-aws/destroy-budgets") {
    description("Pipeline Job for destroying AWS account budgets and alerts.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("global-aws/destroy-budgets/Jenkinsfile.groovy"))
        }
    }
}
