/**
 * Create a Pipeline Job for pushing a 'saints-xctf-api-flask' or 'saints-xctf-api-nginx' Docker image to ECR.
 * @author Andrew Jarombek
 * @since 9/20/2020
 */

pipelineJob("saints-xctf/api/push-image") {
    description("Pipeline Job for pushing a Docker image for the SaintsXCTF API to ECR.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/api/push-image/Jenkinsfile.groovy"))
        }
    }
}
