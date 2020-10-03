/**
 * Destroy AWS and Kubernetes infrastructure for the apollo-client-server-prototype application.
 * @author Andrew Jarombek
 * @since 10/2/2020
 */

pipelineJob("prototypes/apollo-client-server-prototype/destroy-infrastructure") {
    description("Pipeline Job for destroying AWS and Kubernetes infrastructure for the Apollo Client & Server Prototype.")
    definition {
        cps {
            sandbox()
            script(
                readFileFromWorkspace("prototypes/apollo-client-server-prototype/destroy-infrastructure/Jenkinsfile.groovy")
            )
        }
    }
}