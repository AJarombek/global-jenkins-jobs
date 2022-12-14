/**
 * Create a Pipeline Job for running tests on Kubernetes infrastructure defined in the global-aws-infrastructure repo.
 * @author Andrew Jarombek
 * @since 7/5/2020
 */

pipelineJob("global-aws/global-kubernetes-infrastructure-test") {
    description("Pipeline Job for testing the Kubernetes infrastructure defined in the global-aws-infrastructure repository.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("global-aws/global-kubernetes-infrastructure-test/Jenkinsfile.groovy"))
        }
    }
}
