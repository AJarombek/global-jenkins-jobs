/**
 * Create a Pipeline Job for creating AWS infrastructure for SaintsXCTF Functions (fn.saintsxctf.com).
 * @author Andrew Jarombek
 * @since 11/21/2020
 */

pipelineJob("saints-xctf/infrastructure/create-saints-xctf-com-fn") {
    description("Pipeline Job for creating AWS infrastructure for SaintsXCTF Functions")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/infrastructure/create-saints-xctf-com-fn/Jenkinsfile.groovy"))
        }
    }
    parameters {
        booleanParam('autoApply', true, "Whether the Terraform infrastructure should be automatically approved.")
        choiceParam('environment', ['dev', 'prod'], 'Environment to build the infrastructure in.')
    }
}
