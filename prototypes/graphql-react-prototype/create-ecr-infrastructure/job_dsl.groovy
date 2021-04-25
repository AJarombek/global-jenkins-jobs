/**
 * Create ECR repositories for the graphql-react-prototype application.
 * @author Andrew Jarombek
 * @since 6/28/2020
 */

pipelineJob("prototypes/graphql-react-prototype/create-ecr-infrastructure") {
    description("Pipeline Job for creating ECR repositories for the GraphQL React Prototype.")
    definition {
        cps {
            sandbox()
            script(
                readFileFromWorkspace("prototypes/graphql-react-prototype/create-ecr-infrastructure/Jenkinsfile.groovy")
            )
        }
    }
    parameters {
        booleanParam('autoApply', true, "Whether the Terraform infrastructure should be automatically approved.")
    }
}
