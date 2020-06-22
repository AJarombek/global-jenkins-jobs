/**
 * Create a Pipeline Job for pushing a 'rotate' AWS Lambda function Docker image to DockerHub.
 * @author Andrew Jarombek
 * @since 6/22/2020
 */

pipelineJob("saints-xctf/auth/push-rotate-lambda-image") {
    description("Pipeline Job for pushing a Docker image for the rotate AWS Lambda function to DockerHub.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/auth/push-rotate-lambda-image/Jenkinsfile.groovy"))
        }
    }
}
