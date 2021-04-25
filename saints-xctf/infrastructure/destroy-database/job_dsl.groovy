/**
 * Destroy a MySQL database on Amazon RDS for the SaintsXCTF application.
 * @author Andrew Jarombek
 * @since 7/18/2020
 */

pipelineJob("saints-xctf/infrastructure/destroy-database") {
    description("Pipeline Job for destroying a MySQL database on Amazon RDS for the SaintsXCTF application.")
    definition {
        cps {
            sandbox()
            script(
                readFileFromWorkspace("saints-xctf/infrastructure/destroy-database/Jenkinsfile.groovy")
            )
        }
    }
    parameters {
        booleanParam('autoDestroy', true, "Whether the Terraform infrastructure should be automatically destroyed.")
        choiceParam('environment', ['dev'], 'Environment to build the infrastructure in.')
    }
}