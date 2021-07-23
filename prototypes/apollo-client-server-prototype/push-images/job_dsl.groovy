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
    parameters {
        choiceParam(
            'image',
            [
                'apollo-client-server-prototype-web',
                'apollo-client-server-prototype-api-app',
                'apollo-client-server-prototype-api-nginx',
                'apollo-client-server-prototype-database'
            ],
            'Name of the Docker image to push to Dockerhub.'
        )
        stringParam('label', '1.0.0', 'Label/Version of the Docker image to push to Dockerhub')
        booleanParam('isLatest', true, "Whether this Docker image should also be pushed with the 'latest' label")
    }
}