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
}