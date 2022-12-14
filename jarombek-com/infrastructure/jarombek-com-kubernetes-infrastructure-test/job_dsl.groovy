/**
 * Create a Pipeline Job for running tests on Kubernetes infrastructure defined in the jarombek-com-infrastructure repo.
 * @author Andrew Jarombek
 * @since 4/11/2021
 */

def environments = ["prod", "dev"]

environments.each { env ->
    pipelineJob("jarombek-com/infrastructure/jarombek-com-kubernetes-infrastructure-test-$env") {
        description("Pipeline Job for testing the Kubernetes infrastructure defined in the global-aws-infrastructure repository.")
        definition {
            cps {
                sandbox()
                script(readFileFromWorkspace("jarombek-com/infrastructure/jarombek-com-kubernetes-infrastructure-test/Jenkinsfile.groovy"))
            }
        }
    }
}