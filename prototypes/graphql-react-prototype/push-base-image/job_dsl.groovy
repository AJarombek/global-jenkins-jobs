/**
 * Create a Pipeline Job for pushing a 'graphql-react-prototype-base' Docker image to DockerHub.
 * @author Andrew Jarombek
 * @since 6/28/2020
 */

pipelineJob("prototypes/graphql-react-prototype/push-base-image") {
    description("Pipeline Job for pushing a base Docker image for the GraphQL React Prototype to ECR.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("prototypes/graphql-react-prototype/push-base-image/Jenkinsfile.groovy"))
        }
    }
    parameters {
        stringParam('label', '1.0.0', 'Label/Version of the Docker image to push to DockerHub')
        booleanParam('isLatest', true, "Whether this Docker image should also be pushed with the 'latest' label")
    }
}
