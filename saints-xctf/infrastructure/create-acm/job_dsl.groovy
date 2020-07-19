/**
 * Create ACM certificates for the SaintsXCTF application.
 * @author Andrew Jarombek
 * @since 7/18/2020
 */

pipelineJob("saints-xctf/infrastructure/create-acm") {
    description("Pipeline Job for creating ACM certificates for the SaintsXCTF application.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/infrastructure/create-acm/Jenkinsfile.groovy"))
        }
    }
}
