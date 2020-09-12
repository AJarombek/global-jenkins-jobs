/**
 * Create a Pipeline Job for creating AWS infrastructure for SaintsXCTF assets.
 * @author Andrew Jarombek
 * @since 9/12/2020
 */

pipelineJob("saints-xctf/infrastructure/create-saints-xctf-com-asset") {
    description("Pipeline Job for creating AWS infrastructure for SaintsXCTF assets.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/infrastructure/create-saints-xctf-com-asset/Jenkinsfile.groovy"))
        }
    }
}

