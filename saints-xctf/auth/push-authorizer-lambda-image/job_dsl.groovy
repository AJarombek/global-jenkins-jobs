/**
 * Create a Pipeline Job for pushing a 'authorizer' AWS Lambda function Docker image to DockerHub.
 * @author Andrew Jarombek
 * @since 6/22/2020
 */

pipelineJob("saints-xctf/auth/push-authorizer-lambda-image") {
    description("Pipeline Job for pushing a Docker image for the authorizer AWS Lambda function to DockerHub.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/auth/push-authorizer-lambda-image/Jenkinsfile.groovy"))
        }
    }
}
