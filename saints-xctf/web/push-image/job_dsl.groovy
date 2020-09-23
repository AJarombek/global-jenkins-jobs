/**
 * Create a Pipeline Job for pushing a 'saints-xctf-web' Docker image to ECR.
 * @author Andrew Jarombek
 * @since 9/22/2020
 */

pipelineJob("saints-xctf/web/push-image") {
    description("Pipeline Job for pushing a Docker image for the SaintsXCTF web application to ECR.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/web/push-image/Jenkinsfile.groovy"))
        }
    }
}
