/**
 * Create a Pipeline Job for pushing a 'jarombek-com' or 'jarombek-com-database' Docker image to ECR.
 * @author Andrew Jarombek
 * @since 7/26/2021
 */

pipelineJob("jarombek-com/web/push-image") {
    description("Pipeline Job for pushing a Docker image for the jarombek.com application to DockerHub.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("jarombek-com/web/push-image/Jenkinsfile.groovy"))
        }
    }
    parameters {
        choiceParam(
            'image',
            ['jarombek-com-database', 'jarombek-com'],
            'Name of the Docker image and DockerHub repository to push to.'
        )
        stringParam(
            'label',
            '1.1.0',
            'Label/Version of the Docker image to push to DockerHub'
        )
        booleanParam(
            'isLatest',
            true,
            "Whether this Docker image should also be pushed with the 'latest' label"
        )
    }
}
