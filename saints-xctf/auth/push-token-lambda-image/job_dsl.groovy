/**
 * Create a Pipeline Job for building and pushing a AWS Lambda function builder Docker image to DockerHub.
 * @author Andrew Jarombek
 * @since 6/21/2020
 */

pipelineJob("saints-xctf/auth/push-lambda-builder-base-image") {
    description("Pipeline Job for building and pushing a Docker image used to build AWS Lambda functions to DockerHub.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/auth/push-lambda-builder-base-image/Jenkinsfile.groovy"))
        }
    }
}
