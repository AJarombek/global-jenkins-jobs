/**
 * Create a Pipeline Job for deploying the react-webpack-seed project
 * @author Andrew Jarombek
 * @since 3/15/2019
 */

pipelineJob("react-webpack-seed/react-webpack-seed") {
    description("Pipeline Job for deploying the react-webpack-seed project")
    concurrentBuild(false)
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("react-webpack-seed/Jenkinsfile.groovy"))
        }
    }
    logRotator {
        daysToKeep(10)
        numToKeep(5)
    }
}