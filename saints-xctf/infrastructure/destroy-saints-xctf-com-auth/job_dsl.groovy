/**
 * Create a Pipeline Job for destroying AWS infrastructure for SaintsXCTF Auth.  Each environment gets its own job.
 * @author Andrew Jarombek
 * @since 6/11/2020
 */

pipelineJob("saints-xctf/infrastructure/destroy-saints-xctf-com-auth") {
    description("Pipeline Job for destroying AWS infrastructure for SaintsXCTF Auth")
    definition {
        cps {
            sandbox()
            script(
                readFileFromWorkspace("saints-xctf/infrastructure/destroy-saints-xctf-com-auth/Jenkinsfile.groovy")
            )
        }
    }
    parameters {
        booleanParam('autoDestroy', true, "Whether the Terraform infrastructure should be automatically destroyed.")
        choiceParam('environment', ['dev', 'prod'], 'Environment to build the infrastructure in.')
    }
}
