/**
 * Create AWS ACM certificates.
 * @author Andrew Jarombek
 * @since 2/21/2021
 */

pipelineJob("global-aws/create-acm") {
    description("Pipeline Job for creating AWS ACM certificates.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("global-aws/create-acm/Jenkinsfile.groovy"))
        }
    }
    parameters {
        booleanParam('autoApply', true, "Whether the Terraform infrastructure should be automatically approved.")
    }
}
