/**
 * Destroy backup and restore AWS Lambda functions for a SaintsXCTF MySQL database on Amazon RDS.
 * @author Andrew Jarombek
 * @since 7/19/2020
 */

pipelineJob("saints-xctf/infrastructure/destroy-database-snapshot") {
    description(
        "Pipeline Job for destroying backup and restore AWS Lambda functions for a SaintsXCTF MySQL database on Amazon RDS."
    )
    definition {
        cps {
            sandbox()
            script(
                readFileFromWorkspace("saints-xctf/infrastructure/destroy-database-snapshot/Jenkinsfile.groovy")
            )
        }
    }
    parameters {
        booleanParam('autoDestroy', true, "Whether the Terraform infrastructure should be automatically destroyed.")
        choiceParam('environment', ['all', 'dev', 'prod'], 'Environment to build the infrastructure in.')
    }
}