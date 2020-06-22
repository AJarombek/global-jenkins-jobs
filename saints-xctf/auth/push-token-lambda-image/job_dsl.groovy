/**
 * Create a Pipeline Job for pushing a 'token' AWS Lambda function Docker image to DockerHub.
 * @author Andrew Jarombek
 * @since 6/21/2020
 */

pipelineJob("saints-xctf/auth/push-token-lambda-image") {
    description("Pipeline Job for pushing a Docker image for the token AWS Lambda function to DockerHub.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/auth/push-token-lambda-image/Jenkinsfile.groovy"))
        }
    }
}
