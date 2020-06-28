/**
 * Create a Pipeline Job for pushing a 'graphql-react-prototype-app' Docker image to DockerHub.
 * @author Andrew Jarombek
 * @since 6/28/2020
 */

pipelineJob("prototypes/graphql-react-prototype/push-app-image") {
    description("Pipeline Job for pushing an application server Docker image for the GraphQL React Prototype to ECR.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("prototypes/graphql-react-prototype/push-app-image/Jenkinsfile.groovy"))
        }
    }
}
