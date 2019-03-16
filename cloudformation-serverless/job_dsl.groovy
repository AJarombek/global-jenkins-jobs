/**
 * Create a Pipeline Job for creating and deploying a CloudFormation template
 * for a Python serverless backend prototype
 * @author Andrew Jarombek
 * @since 11/27/2018
 */

pipelineJob("cloudformation-serverless") {
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("cloudformation-serverless/Jenkinsfile.groovy"))
        }
    }
    wrappers {
        colorizeOutput()
    }
}