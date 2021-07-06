/**
 * Create CloudWatch Synthetic Monitoring Canary functions for the SaintsXCTF application.
 * @author Andrew Jarombek
 * @since 7/5/2021
 */

pipelineJob("saints-xctf/infrastructure/create-synthetic-monitoring") {
    description("Pipeline Job for creating CloudWatch Synthetic Monitoring Canary functions for the SaintsXCTF application.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/infrastructure/create-synthetic-monitoring/Jenkinsfile.groovy"))
        }
    }
    parameters {
        booleanParam('autoDestroy', true, "Whether the Terraform infrastructure should be automatically created.")
        choiceParam('environment', ['all', 'dev', 'prod'], 'Environment to create the Synthetic Monitoring infrastructure.')
    }
}