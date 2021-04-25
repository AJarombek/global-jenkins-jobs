/**
 * Pipeline Job for creating AWS infrastructure for SaintsXCTF API.
 * @author Andrew Jarombek
 * @since 7/26/2020
 */

pipelineJob("saints-xctf/infrastructure/create-saints-xctf-com-api") {
    description("Pipeline Job for destroying AWS infrastructure for the SaintsXCTF API")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/infrastructure/create-saints-xctf-com-api/Jenkinsfile.groovy"))
        }
    }
    parameters {
        booleanParam('autoApply', true, "Whether the Terraform infrastructure should be automatically approved.")
        choiceParam('environment', ['all', 'dev', 'prod'], 'Environment to build the infrastructure in.')
    }
}