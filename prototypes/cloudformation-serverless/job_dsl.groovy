/**
 * Create a Pipeline Job for creating and deploying a CloudFormation template
 * for a Python serverless backend prototype
 * @author Andrew Jarombek
 * @since 11/27/2018
 */

pipelineJob("prototypes/cloudformation-serverless") {
    description("Pipeline Job for creating and deploying a CloudFormation template")
    concurrentBuild(true)
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("prototypes/cloudformation-serverless/Jenkinsfile.groovy"))
        }
    }
    logRotator {
        daysToKeep(10)
        numToKeep(5)
    }
}