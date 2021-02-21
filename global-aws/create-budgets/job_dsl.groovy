/**
 * Create AWS account budgets and alerts.
 * @author Andrew Jarombek
 * @since 2/20/2021
 */

pipelineJob("global-aws/create-budget") {
    description("Pipeline Job for creating AWS account budgets and alerts.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("global-aws/create-budget/Jenkinsfile.groovy"))
        }
    }
}
