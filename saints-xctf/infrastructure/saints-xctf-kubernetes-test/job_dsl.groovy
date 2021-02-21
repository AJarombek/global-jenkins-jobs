/**
 * Create a Pipeline Job for testing the Kubernetes infrastructure defined in the saints-xctf-infrastructure repository.
 * @author Andrew Jarombek
 * @since 2/21/2021
 */

def environments = ["prod", "dev"]

environments.each { env ->
    pipelineJob("saints-xctf/infrastructure/saints-xctf-kubernetes-test-$env") {
        description("Pipeline Job for testing the Kubernetes infrastructure defined in the saints-xctf-infrastructure repository.")
        definition {
            cps {
                sandbox()
                script(readFileFromWorkspace("saints-xctf/infrastructure/saints-xctf-kubernetes-test/Jenkinsfile.groovy"))
            }
        }
    }
}
