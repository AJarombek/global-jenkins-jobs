/**
 * Create a Pipeline Job that destroys AWS infrastructure for SaintsXCTF Functions (fn.saintsxctf.com).
 * @author Andrew Jarombek
 * @since 11/21/2020
 */

pipelineJob("saints-xctf/infrastructure/destroy-saints-xctf-com-fn") {
    description("Pipeline Job that destroys AWS infrastructure for SaintsXCTF Functions")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/infrastructure/destroy-saints-xctf-com-fn/Jenkinsfile.groovy"))
        }
    }
}
