/**
 * Create a Pipeline Job for creating and deploying a CloudFormation template
 * for a Python serverless backend prototype
 * @author Andrew Jarombek
 * @since 11/21/2018
 */

pipelineJob("cloudformation-serverless") {
    definition {
        cpsScm {
            scm {
                git {
                    branch("master")
                    remote {
                        credentials("865da7f9-6fc8-49f3-aa56-8febd149e72b")
                        github("AJarombek/global-jenkins-jobs", "ssh", "github.com")
                    }
                }
            }
            scriptPath("cloudformation-serverless/jenkinsfile.groovy")
        }
    }
}