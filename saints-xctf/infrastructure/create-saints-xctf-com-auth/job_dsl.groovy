/**
 * Create a Pipeline Job for creating AWS infrastructure for SaintsXCTF Auth.
 * @author Andrew Jarombek
 * @since 6/11/2020
 */

pipelineJob("saints-xctf/infrastructure/create-saints-xctf-com-auth") {
    description("Pipeline Job for creating AWS infrastructure for SaintsXCTF Auth")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/infrastructure/create-saints-xctf-com-auth/Jenkinsfile.groovy"))
        }
    }
}
