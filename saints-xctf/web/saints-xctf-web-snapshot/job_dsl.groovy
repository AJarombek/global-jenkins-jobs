/**
 * Create a Pipeline Job for snapshot testing React code in the saints-xctf-web project.
 * @author Andrew Jarombek
 * @since 4/26/2021
 */

pipelineJob("saints-xctf/web/saints-xctf-web-snapshot") {
    description("Pipeline Job for snapshot testing the saints-xctf-web project")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/web/saints-xctf-web-snapshot/Jenkinsfile.groovy"))
        }
    }
}