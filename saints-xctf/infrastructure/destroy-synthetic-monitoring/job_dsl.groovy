/**
 * Destroy CloudWatch Synthetic Monitoring Canary functions for the SaintsXCTF application.
 * @author Andrew Jarombek
 * @since 7/5/2021
 */

pipelineJob("saints-xctf/infrastructure/destroy-synthetic-monitoring") {
    description("Pipeline Job for destroying CloudWatch Synthetic Monitoring Canary functions for the SaintsXCTF application.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/infrastructure/destroy-synthetic-monitoring/Jenkinsfile.groovy"))
        }
    }
    parameters {
        booleanParam('autoDestroy', true, "Whether the Terraform infrastructure should be automatically destroyed.")
        choiceParam('environment', ['all', 'dev', 'prod'], 'Environment to destroy the Synthetic Monitoring infrastructure.')
    }
}