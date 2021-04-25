/**
 * Destroy AWS ACM certificates.
 * @author Andrew Jarombek
 * @since 2/21/2021
 */

pipelineJob("global-aws/destroy-acm") {
    description("Pipeline Job for destroying AWS ACM certificates.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("global-aws/destroy-acm/Jenkinsfile.groovy"))
        }
    }
    parameters {
        booleanParam('autoDestroy', true, "Whether the Terraform infrastructure should be automatically destroyed.")
    }
}
