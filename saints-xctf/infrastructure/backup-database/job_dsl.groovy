/**
 * Backup a database and place the backup SQL file in S3.
 * @author Andrew Jarombek
 * @since 7/19/2020
 */

pipelineJob("saints-xctf/infrastructure/backup-database") {
    description("Pipeline Job for backing up a database and placing the backup SQL file in S3.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/infrastructure/backup-database/Jenkinsfile.groovy"))
        }
    }
}
