/**
 * Create a Pipeline Job which destroys Kubernetes Ingress object infrastructure for SaintsXCTF.
 * @author Andrew Jarombek
 * @since 11/22/2020
 */

pipelineJob("saints-xctf/infrastructure/destroy-saints-xctf-com-ingress") {
    description("Pipeline Job destroying Kubernetes Ingress object infrastructure for SaintsXCTF")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/infrastructure/destroy-saints-xctf-com-ingress/Jenkinsfile.groovy"))
        }
    }
    parameters {
        booleanParam('autoDestroy', true, "Whether the Terraform infrastructure should be automatically destroyed.")
        choiceParam('environment', ['dev', 'prod'], 'Environment to build the infrastructure in.')
    }
}
