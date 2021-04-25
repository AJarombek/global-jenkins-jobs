/**
 * Create secrets in SecretsManager for the SaintsXCTF application.
 * @author Andrew Jarombek
 * @since 9/12/2020
 */

pipelineJob("saints-xctf/infrastructure/database-script-deployment") {
    description("Pipeline Job for creating secrets in SecretsManager for the SaintsXCTF application.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/infrastructure/database-script-deployment/Jenkinsfile.groovy"))
        }
    }
    parameters {
        choiceParam('environment', ['dev', 'prod'], 'Environment to create the secrets.')
        stringParam('scriptPath', 'staging/<filename>.sql', 'File path in the saints-xctf-database repository of a SQL script.')
    }
}