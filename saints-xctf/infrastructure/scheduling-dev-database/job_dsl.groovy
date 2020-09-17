/**
 * Schedule start and stop times for the SaintsXCTF development RDS database.
 * @author Andrew Jarombek
 * @since 9/16/2020
 */

pipelineJob("saints-xctf/infrastructure/scheduling-dev-database") {
    description("Pipeline Job scheduling start and stop times for the SaintsXCTF development RDS database.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/infrastructure/scheduling-dev-database/Jenkinsfile.groovy"))
        }
    }
}
