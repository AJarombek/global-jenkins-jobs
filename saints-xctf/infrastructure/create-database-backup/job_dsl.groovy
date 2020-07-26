/**
 * Create an S3 bucket which holds backup files for a MySQL RDS database
 * @author Andrew Jarombek
 * @since 7/26/2020
 */

pipelineJob("saints-xctf/infrastructure/create-database-backup") {
    description("Pipeline Job for creating an S3 bucket which holds backup files for a MySQL RDS database.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/infrastructure/create-database-backup/Jenkinsfile.groovy"))
        }
    }
}