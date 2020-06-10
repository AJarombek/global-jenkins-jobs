/**
 * Create a Pipeline Job for building and pushing a Jenkins Docker image to an ECR repository.
 * @author Andrew Jarombek
 * @since 6/10/2020
 */

pipelineJob("global-aws/push-jenkins-ecr-docker-image") {
    description("Pipeline Job for building and pushing a Jenkins Docker image to an ECR repository.")
    concurrentBuild(true)
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("global-aws/push-jenkins-ecr-docker-image/Jenkinsfile.groovy"))
        }
    }
}
