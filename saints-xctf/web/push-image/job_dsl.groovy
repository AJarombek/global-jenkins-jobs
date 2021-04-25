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
    parameters {
        choiceParam(
            'image',
            ['saints-xctf-web-base', 'saints-xctf-web-nginx', 'saints-xctf-web-nginx-dev'],
            'Name of the Docker image and ECR repository to push to.'
        )
        stringParam(
            'label',
            '1.1.3',
            'Label/Version of the Docker image to push to ECR'
        )
        booleanParam(
            'isLatest',
            true,
            "Whether this Docker image should also be pushed with the 'latest' label"
        )
    }
}
