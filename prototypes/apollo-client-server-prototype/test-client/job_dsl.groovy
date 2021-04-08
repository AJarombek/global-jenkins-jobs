/**
 * Test the Apollo Client React codebase in the apollo-client-server-prototype application.
 * @author Andrew Jarombek
 * @since 4/7/2021
 */

pipelineJob("prototypes/apollo-client-server-prototype/test-client") {
    description("Test the Apollo Client React codebase in the apollo-client-server-prototype application.")
    definition {
        cps {
            sandbox()
            script(
                readFileFromWorkspace("prototypes/apollo-client-server-prototype/test-client/Jenkinsfile.groovy")
            )
        }
    }
}