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
    parameters {
        choiceParam(
            'image',
            ['saints-xctf-api-flask', 'saints-xctf-api-nginx', 'saints-xctf-api-cicd'],
            'Name of the Docker image and ECR repository to push to.'
        )
        stringParam('label', '2.0.2', 'Label/Version of the Docker image to push to ECR')
        booleanParam('isLatest', true, "Whether this Docker image should also be pushed with the 'latest' label")
    }
}
