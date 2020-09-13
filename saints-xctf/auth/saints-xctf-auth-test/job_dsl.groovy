/**
 * Create a Pipeline Job for running tests of the auth.saintsxctf.com API.
 * @author Andrew Jarombek
 * @since 9/13/2020
 */

pipelineJob("saints-xctf/auth/saints-xctf-auth-test") {
    description("Pipeline Job for testing the auth.saintsxctf.com API.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/auth/saints-xctf-auth-test/Jenkinsfile.groovy"))
        }
    }
}
