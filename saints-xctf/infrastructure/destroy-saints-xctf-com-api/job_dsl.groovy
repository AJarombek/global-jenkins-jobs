/**
 * Pipeline Job for destroying AWS infrastructure for SaintsXCTF API.
 * @author Andrew Jarombek
 * @since 7/26/2020
 */

pipelineJob("saints-xctf/infrastructure/destroy-saints-xctf-com-api") {
    description("Pipeline Job for destroying AWS infrastructure for the SaintsXCTF API")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/infrastructure/destroy-saints-xctf-com-api/Jenkinsfile.groovy"))
        }
    }
}
