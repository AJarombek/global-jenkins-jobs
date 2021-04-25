/**
 * Create secrets in SecretsManager for the SaintsXCTF application.
 * @author Andrew Jarombek
 * @since 7/19/2020
 */

pipelineJob("saints-xctf/infrastructure/create-secrets-manager") {
    description("Pipeline Job for creating secrets in SecretsManager for the SaintsXCTF application.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/infrastructure/create-secrets-manager/Jenkinsfile.groovy"))
        }
    }
    parameters {
        booleanParam('autoApply', true, "Whether the Terraform infrastructure should be automatically approved.")
        choiceParam('environment', ['dev', 'prod'], 'Environment to create the secrets.')
        stringParam('rdsPasswordSecret', '', 'Password for the RDS database.')
    }
}