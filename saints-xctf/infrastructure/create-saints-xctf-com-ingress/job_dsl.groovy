/**
 * Create a Pipeline Job which creates Kubernetes Ingress object infrastructure for SaintsXCTF.
 * @author Andrew Jarombek
 * @since 11/22/2020
 */

pipelineJob("saints-xctf/infrastructure/create-saints-xctf-com-ingress") {
    description("Pipeline Job creating Kubernetes Ingress object infrastructure for SaintsXCTF")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/infrastructure/create-saints-xctf-com-ingress/Jenkinsfile.groovy"))
        }
    }
    parameters {
        booleanParam('autoApply', true, "Whether the Terraform infrastructure should be automatically approved.")
        choiceParam('environment', ['dev', 'prod'], 'Environment to build the infrastructure in.')
    }
}
