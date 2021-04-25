/**
 * Create a database deployment AWS Lambda function for a SaintsXCTF MySQL database on Amazon RDS.
 * @author Andrew Jarombek
 * @since 9/19/2020
 */

pipelineJob("saints-xctf/infrastructure/create-database-deployment") {
    description(
        "Pipeline Job for creating a database deployment AWS Lambda function for a SaintsXCTF MySQL database on Amazon RDS."
    )
    definition {
        cps {
            sandbox()
            script(
                readFileFromWorkspace("saints-xctf/infrastructure/create-database-deployment/Jenkinsfile.groovy")
            )
        }
    }
    parameters {
        booleanParam('autoApply', true, "Whether the Terraform infrastructure should be automatically approved.")
        choiceParam('environment', ['all', 'dev', 'prod'], 'Environment to build the infrastructure in.')
    }
}