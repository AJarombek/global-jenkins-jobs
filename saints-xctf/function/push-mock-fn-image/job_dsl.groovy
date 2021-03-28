/**
 * Push a mock implementation image to Dockerhub for the saints-xctf-function application.
 * @author Andrew Jarombek
 * @since 3/28/2021
 */

pipelineJob("saints-xctf/function/push-mock-fn-image") {
    description("Pipeline Job to push a mock implementation image to Dockerhub for the saints-xctf-function application.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/function/push-mock-fn-image/Jenkinsfile.groovy"))
        }
    }
}