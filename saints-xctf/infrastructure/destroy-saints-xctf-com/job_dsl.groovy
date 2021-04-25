/**
 * Pipeline Job for destroying AWS infrastructure for SaintsXCTF web application.
 * @author Andrew Jarombek
 * @since 7/25/2020
 */

pipelineJob("saints-xctf/infrastructure/destroy-saints-xctf-com") {
    description("Pipeline Job for destroying AWS infrastructure for the SaintsXCTF web application")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/infrastructure/destroy-saints-xctf-com/Jenkinsfile.groovy"))
        }
    }
    parameters {
        booleanParam('autoDestroy', true, "Whether the Terraform infrastructure should be automatically destroyed.")
        choiceParam('environment', ['all', 'dev'], 'Environment to build the infrastructure in.')
    }
}
