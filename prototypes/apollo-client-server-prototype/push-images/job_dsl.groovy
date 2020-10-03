/**
 * Push images to Dockerhub for the apollo-client-server-prototype application.
 * @author Andrew Jarombek
 * @since 10/2/2020
 */

pipelineJob("prototypes/apollo-client-server-prototype/push-images") {
    description("Pipeline Job that pushes Docker images to Dockerhub for the Apollo Client & Server Prototype.")
    definition {
        cps {
            sandbox()
            script(
                readFileFromWorkspace("prototypes/apollo-client-server-prototype/push-images/Jenkinsfile.groovy")
            )
        }
    }
}