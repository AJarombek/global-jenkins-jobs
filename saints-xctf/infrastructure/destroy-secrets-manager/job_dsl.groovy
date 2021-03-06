/**
 * Destroy secrets in SecretsManager for the SaintsXCTF application.
 * @author Andrew Jarombek
 * @since 7/19/2020
 */

pipelineJob("saints-xctf/infrastructure/destroy-secrets-manager") {
    description("Pipeline Job for destroying secrets in SecretsManager for the SaintsXCTF application.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/infrastructure/destroy-secrets-manager/Jenkinsfile.groovy"))
        }
    }
    parameters {
        booleanParam('autoDestroy', true, "Whether the Terraform infrastructure should be automatically destroyed.")
        choiceParam('environment', ['dev', 'prod'], 'Environment to destroy the secrets.')
    }
}