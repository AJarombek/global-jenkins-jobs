/**
 * Destroy an S3 bucket which holds backup files for a MySQL RDS database
 * @author Andrew Jarombek
 * @since 7/26/2020
 */

pipelineJob("saints-xctf/infrastructure/destroy-database-backup") {
    description("Pipeline Job for destroying an S3 bucket which holds backup files for a MySQL RDS database.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/infrastructure/destroy-database-backup/Jenkinsfile.groovy"))
        }
    }
}