/**
 * Create a Pipeline Job for pushing Docker images used throughout my infrastructure to DockerHub.
 * @author Andrew Jarombek
 * @since 7/4/2020
 */

pipelineJob("global-aws/dockerfiles-push-images") {
    description("Pipeline Job for pushing Docker images used throughout my infrastructure to DockerHub.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("global-aws/dockerfiles-push-images/Jenkinsfile.groovy"))
        }
    }
}
