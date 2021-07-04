/**
 * Destroy ACM certificates for the graphql-react-prototype application.
 * @author Andrew Jarombek
 * @since 7/4/2021
 */

pipelineJob("prototypes/graphql-react-prototype/destroy-acm-infrastructure") {
    description("Pipeline Job for destroying ACM certificates for the GraphQL React Prototype.")
    definition {
        cps {
            sandbox()
            script(
                readFileFromWorkspace("prototypes/graphql-react-prototype/destroy-acm-infrastructure/Jenkinsfile.groovy")
            )
        }
    }
    parameters {
        booleanParam('autoDestroy', true, "Whether the Terraform infrastructure should be automatically destroyed.")
    }
}
