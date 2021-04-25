/**
 * Create a Pipeline Job for linting TypeScript code in the saints-xctf-web project.
 * @author Andrew Jarombek
 * @since 3/14/2020
 */

pipelineJob("saints-xctf/web/saints-xctf-web-lint") {
    description("Pipeline Job for linting the saints-xctf-web project")
    triggers {
        cron('H 0 * * *')
    }
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/web/saints-xctf-web-lint/Jenkinsfile.groovy"))
        }
    }
}