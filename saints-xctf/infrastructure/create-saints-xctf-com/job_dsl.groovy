/**
 * Pipeline Job for creating AWS infrastructure for the SaintsXCTF web application.
 * @author Andrew Jarombek
 * @since 7/25/2020
 */

pipelineJob("saints-xctf/infrastructure/create-saints-xctf-com") {
    description("Pipeline Job for creating AWS infrastructure for the SaintsXCTF web application")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/infrastructure/create-saints-xctf-com/Jenkinsfile.groovy"))
        }
    }
}