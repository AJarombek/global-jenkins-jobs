/**
 * Push a mock implementation image to Dockerhub for the saints-xctf-auth application.
 * @author Andrew Jarombek
 * @since 10/11/2020
 */

pipelineJob("saints-xctf/auth/push-mock-auth-image") {
    description("Pipeline Job to push a mock implementation image to Dockerhub for the saints-xctf-auth application.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/auth/push-mock-auth-image/Jenkinsfile.groovy"))
        }
    }
    parameters {
        stringParam('label', '1.0.0', 'Label/Version of the Docker image to push to Dockerhub')
        booleanParam('isLatest', true, "Whether this Docker image should also be pushed with the 'latest' label")
    }
}