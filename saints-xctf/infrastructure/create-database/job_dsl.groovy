/**
 * Create a MySQL database on Amazon RDS for the SaintsXCTF application.
 * @author Andrew Jarombek
 * @since 7/18/2020
 */

pipelineJob("saints-xctf/infrastructure/create-database") {
    description("Pipeline Job for creating a MySQL database on Amazon RDS for the SaintsXCTF application.")
    definition {
        cps {
            sandbox()
            script(
                readFileFromWorkspace("saints-xctf/infrastructure/create-database/Jenkinsfile.groovy")
            )
        }
    }
    parameters {
        booleanParam('autoApply', true, "Whether the Terraform infrastructure should be automatically approved.")
        choiceParam('environment', ['dev'], 'Environment to build the infrastructure in.')
    }
}
