/**
 * Create a Pipeline Job for creating AWS infrastructure for Jenkins ECS.  Each environment gets its own job.
 * @author Andrew Jarombek
 * @since 6/11/2020
 */

def environments = ["prod", "dev", "all"]

environments.each { env ->
    pipelineJob("global-aws/create-jenkins-ecs-$env") {
        description(
            "Pipeline Job for creating AWS infrastructure for Jenkins ECS in the ${env.capitalize()} environment"
        )
        definition {
            cps {
                sandbox()
                script(
                    readFileFromWorkspace(
                        "global-aws/create-jenkins-ecs/Jenkinsfile.groovy"
                    )
                )
            }
        }
    }
}
