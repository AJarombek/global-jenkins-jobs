/**
 * Create a Pipeline Job for baking AMIs for the Jenkins server
 * @author Andrew Jarombek
 * @since 1/24/2019
 */

pipelineJob("jenkins-bake-ami") {
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
            scriptPath("jenkins-bake-ami/Jenkinsfile.groovy")
        }
    }
}
