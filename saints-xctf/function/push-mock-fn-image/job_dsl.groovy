/**
 * Push a mock implementation image to Dockerhub for the saints-xctf-function application.
 * @author Andrew Jarombek
 * @since 3/28/2021
 */

pipelineJob("saints-xctf/function/push-mock-fn-image") {
    description("Pipeline Job to push a mock implementation image to Dockerhub for the saints-xctf-function application.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/function/push-mock-fn-image/Jenkinsfile.groovy"))
        }
    }
    parameters {
        stringParam('label', '1.0.0', 'Label/Version of the Docker image to push to Dockerhub')
        booleanParam('isLatest', true, "Whether this Docker image should also be pushed with the 'latest' label")
    }
}