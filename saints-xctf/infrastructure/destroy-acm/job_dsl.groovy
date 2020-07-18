/**
 * Destroy ACM certificates for the SaintsXCTF application.
 * @author Andrew Jarombek
 * @since 7/18/2020
 */

pipelineJob("saints-xctf/infrastructure/destroy-acm") {
    description("Pipeline Job for destroying ACM certificates for the SaintsXCTF application.")
    definition {
        cps {
            sandbox()
            script(
                readFileFromWorkspace("saints-xctf/infrastructure/destroy-acm/Jenkinsfile.groovy")
            )
        }
    }
}