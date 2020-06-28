/**
 * Create a Pipeline Job for creating AWS infrastructure for SaintsXCTF Auth.  Each environment gets its own job.
 * @author Andrew Jarombek
 * @since 6/11/2020
 */

def environments = ["prod", "dev", "all"]

environments.each { env ->
    pipelineJob("saints-xctf/infrastructure/create-saints-xctf-com-auth-$env") {
        description(
            "Pipeline Job for creating AWS infrastructure for SaintsXCTF Auth in the ${env.capitalize()} environment"
        )
        definition {
            cps {
                sandbox()
                script(
                    readFileFromWorkspace(
                        "saints-xctf/infrastructure/create-saints-xctf-com-auth/Jenkinsfile.groovy"
                    )
                )
            }
        }
    }
}
