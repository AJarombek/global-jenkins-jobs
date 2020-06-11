/**
 * Create a Pipeline Job for destroying AWS infrastructure for Jenkins ECS.  Each environment gets its own job.
 * @author Andrew Jarombek
 * @since 6/11/2020
 */

def environments = ["prod", "dev", "all"]

environments.each { env ->
    pipelineJob("global-aws/destroy-jenkins-ecs-$env") {
        description(
            "Pipeline Job for destroying AWS infrastructure for Jenkins ECS in the ${env.capitalize()} environment"
        )
        definition {
            cps {
                sandbox()
                script(
                    readFileFromWorkspace(
                        "global-aws/destroy-jenkins-ecs/Jenkinsfile.groovy"
                    )
                )
            }
        }
    }
}
