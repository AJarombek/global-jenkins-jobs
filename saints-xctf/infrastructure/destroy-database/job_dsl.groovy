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
}