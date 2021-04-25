/**
 * Create Kubernetes infrastructure for the graphql-react-prototype application.
 * The K8s objects are created on my AWS EKS cluster.
 * @author Andrew Jarombek
 * @since 6/28/2020
 */

pipelineJob("prototypes/graphql-react-prototype/create-k8s-infrastructure") {
    description("Pipeline Job for creating Kubernetes infrastructure for the GraphQL React Prototype.")
    definition {
        cps {
            sandbox()
            script(
                readFileFromWorkspace("prototypes/graphql-react-prototype/create-k8s-infrastructure/Jenkinsfile.groovy")
            )
        }
    }
    parameters {
        booleanParam('autoApply', true, "Whether the Terraform infrastructure should be automatically approved.")
    }
}
