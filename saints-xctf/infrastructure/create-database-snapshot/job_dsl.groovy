/**
 * Create backup and restore AWS Lambda functions for a SaintsXCTF MySQL database on Amazon RDS.
 * @author Andrew Jarombek
 * @since 7/19/2020
 */

pipelineJob("saints-xctf/infrastructure/create-database-snapshot") {
    description(
        "Pipeline Job for creating backup and restore AWS Lambda functions for a SaintsXCTF MySQL database on Amazon RDS."
    )
    definition {
        cps {
            sandbox()
            script(
                readFileFromWorkspace("saints-xctf/infrastructure/create-database-snapshot/Jenkinsfile.groovy")
            )
        }
    }
    parameters {
        booleanParam('autoApply', true, "Whether the Terraform infrastructure should be automatically approved.")
        choiceParam('environment', ['all', 'dev', 'prod'], 'Environment to build the infrastructure in.')
    }
}