/**
 * Destroy a database deployment AWS Lambda Function for the SaintsXCTF MySQL database on Amazon RDS.
 * @author Andrew Jarombek
 * @since 9/19/2020
 */

pipelineJob("saints-xctf/infrastructure/destroy-database-deployment") {
    description(
        "Pipeline Job for destroying a database deployment AWS Lambda Function for the SaintsXCTF MySQL database on Amazon RDS."
    )
    definition {
        cps {
            sandbox()
            script(
                readFileFromWorkspace("saints-xctf/infrastructure/destroy-database-deployment/Jenkinsfile.groovy")
            )
        }
    }
}