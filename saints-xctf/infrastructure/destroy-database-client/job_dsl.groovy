/**
 * Pipeline Job for destroying Kubernetes and AWS infrastructure for the SaintsXCTF database client (db.saintsxctf.com).
 * @author Andrew Jarombek
 * @since 3/17/2021
 */

pipelineJob("saints-xctf/infrastructure/destroy-database-client") {
    description("Pipeline Job for destroying Kubernetes and AWS infrastructure for the SaintsXCTF database client (db.saintsxctf.com)")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/infrastructure/destroy-database-client/Jenkinsfile.groovy"))
        }
    }
}
