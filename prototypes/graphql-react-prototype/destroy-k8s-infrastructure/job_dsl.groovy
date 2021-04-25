/**
 * Destroy Kubernetes infrastructure for the graphql-react-prototype application.
 * The K8s objects are created on my AWS EKS cluster.
 * @author Andrew Jarombek
 * @since 6/28/2020
 */

pipelineJob("prototypes/graphql-react-prototype/destroy-k8s-infrastructure") {
    description("Pipeline Job for destroying Kubernetes infrastructure for the GraphQL React Prototype.")
    definition {
        cps {
            sandbox()
            script(
                readFileFromWorkspace("prototypes/graphql-react-prototype/destroy-k8s-infrastructure/Jenkinsfile.groovy")
            )
        }
    }
    parameters {
        booleanParam('autoDestroy', true, "Whether the Terraform infrastructure should be automatically destroyed.")
    }
}
