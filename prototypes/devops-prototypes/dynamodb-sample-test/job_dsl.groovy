/**
 * Create a Pipeline Job for creating a DynamoDB table with items.  This infrastructure is created with Terraform,
 * tested with Python, and modified with Go.
 * @author Andrew Jarombek
 * @since 2/27/2021
 */

pipelineJob("prototypes/devops-prototypes/dynamodb-sample-test") {
    description("Pipeline Job for testing the DynamoDB DevOps Prototype sample code.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("prototypes/devops-prototypes/dynamodb-sample-test/Jenkinsfile.groovy"))
        }
    }
}
