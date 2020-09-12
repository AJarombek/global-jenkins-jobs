/**
 * Create a Pipeline Job for creating AWS infrastructure for SaintsXCTF user assets.
 * @author Andrew Jarombek
 * @since 9/12/2020
 */

pipelineJob("saints-xctf/infrastructure/create-saints-xctf-com-uasset") {
    description("Pipeline Job for creating AWS infrastructure for SaintsXCTF user assets.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/infrastructure/create-saints-xctf-com-uasset/Jenkinsfile.groovy"))
        }
    }
}

