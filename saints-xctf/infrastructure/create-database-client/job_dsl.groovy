/**
 * Pipeline Job for creating Kubernetes and AWS infrastructure for the SaintsXCTF database client (db.saintsxctf.com).
 * @author Andrew Jarombek
 * @since 3/17/2021
 */

pipelineJob("saints-xctf/infrastructure/create-database-client") {
    description("Pipeline Job for creating Kubernetes and AWS infrastructure for the SaintsXCTF database client (db.saintsxctf.com)")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/infrastructure/create-database-client/Jenkinsfile.groovy"))
        }
    }
    parameters {
        booleanParam('autoApply', true, "Whether the Terraform infrastructure should be automatically approved.")
        stringString('cidr', '0.0.0.0/0', 'CIDR block that has access to the SaintsXCTF database client (the db.saintsxctf.com domain).')
    }
}