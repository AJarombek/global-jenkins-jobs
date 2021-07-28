/**
 * Test the Apollo Server Node.js/Express codebase in the apollo-client-server-prototype application.
 * @author Andrew Jarombek
 * @since 7/27/2021
 */

pipelineJob("prototypes/apollo-client-server-prototype/test-server") {
    description("Test the Apollo Server Node.js/Express codebase in the apollo-client-server-prototype application.")
    definition {
        cps {
            sandbox()
            script(
                readFileFromWorkspace("prototypes/apollo-client-server-prototype/test-server/Jenkinsfile.groovy")
            )
        }
    }
}