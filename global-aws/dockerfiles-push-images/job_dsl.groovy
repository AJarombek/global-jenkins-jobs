/**
 * Create a Pipeline Job for pushing Docker images used throughout my infrastructure to DockerHub.
 * @author Andrew Jarombek
 * @since 7/4/2020
 */

pipelineJob("global-aws/dockerfiles-push-images") {
    description("Pipeline Job for pushing Docker images used throughout my infrastructure to DockerHub.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("global-aws/dockerfiles-push-images/Jenkinsfile.groovy"))
        }
    }
    parameters {
        choiceParam('image', ['mysql-aws', 'pipenv-flask'], 'Name of the Docker image to push to Dockerhub.')
        stringParam('label', '1.0.0', 'Label/Version of the Docker image to push to Dockerhub')
        booleanParam('isLatest', true, "Whether this Docker image should also be pushed with the 'latest' label")
    }
}
