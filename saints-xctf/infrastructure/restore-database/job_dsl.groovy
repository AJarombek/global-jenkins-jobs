/**
 * Restore a database from a backup.
 * @author Andrew Jarombek
 * @since 7/19/2020
 */

pipelineJob("saints-xctf/infrastructure/restore-database") {
    description("Pipeline Job for restoring a database from a backup.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/infrastructure/restore-database/Jenkinsfile.groovy"))
        }
    }
    parameters {
        choiceParam('environment', ['dev'], 'Environment of the database.')
    }
}
