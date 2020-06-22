/**
 * Create a Pipeline Job for pushing a 'authenticate' AWS Lambda function Docker image to DockerHub.
 * @author Andrew Jarombek
 * @since 6/22/2020
 */

pipelineJob("saints-xctf/auth/push-authenticate-lambda-image") {
    description("Pipeline Job for pushing a Docker image for the authenticate AWS Lambda function to DockerHub.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/auth/push-authenticate-lambda-image/Jenkinsfile.groovy"))
        }
    }
}
