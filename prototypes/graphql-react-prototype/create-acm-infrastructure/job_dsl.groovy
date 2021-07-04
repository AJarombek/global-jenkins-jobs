/**
 * Create ACM certificates for the graphql-react-prototype application.
 * @author Andrew Jarombek
 * @since 7/4/2021
 */

pipelineJob("prototypes/graphql-react-prototype/create-acm-infrastructure") {
    description("Pipeline Job for creating ACM certificates for the GraphQL React Prototype.")
    definition {
        cps {
            sandbox()
            script(
                readFileFromWorkspace("prototypes/graphql-react-prototype/create-acm-infrastructure/Jenkinsfile.groovy")
            )
        }
    }
    parameters {
        booleanParam('autoApply', true, "Whether the Terraform infrastructure should be automatically approved.")
    }
}
