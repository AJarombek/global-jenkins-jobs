/**
 * Destroy ECR repositories for the graphql-react-prototype application.
 * @author Andrew Jarombek
 * @since 6/28/2020
 */

pipelineJob("prototypes/graphql-react-prototype/destroy-ecr-infrastructure") {
    description("Pipeline Job for destroying ECR repositories for the GraphQL React Prototype.")
    definition {
        cps {
            sandbox()
            script(
                readFileFromWorkspace("prototypes/graphql-react-prototype/destroy-ecr-infrastructure/Jenkinsfile.groovy")
            )
        }
    }
    parameters {
        booleanParam('autoDestroy', true, "Whether the Terraform infrastructure should be automatically destroyed.")
    }
}
