/**
 * Create a Pipeline Job for end to end testing the saints-xctf-web project.
 * @author Andrew Jarombek
 * @since 5/2/2021
 */

pipelineJob("saints-xctf/web/saints-xctf-web-e2e") {
    description("Pipeline Job for end to end testing the saints-xctf-web project")
    triggers {
        cron('H 0 * * *')
    }
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/web/saints-xctf-web-e2e/Jenkinsfile.groovy"))
        }
    }
}