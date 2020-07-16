/**
 * Create a Pipeline Job for setting the folder structure of the Jenkins server.
 * @author Andrew Jarombek
 * @since 7/15/2020
 */

pipelineJob("set-folders") {
    description("Pipeline Job for setting the folder structure of the Jenkins server")
    definition {
        cpsScm {
            scm {
                git {
                    branch("master")
                    remote {
                        credentials("ajarombek-github")
                        github("AJarombek/global-jenkins-jobs", "ssh", "github.com")
                    }
                }
                scriptPath("bootstrap/set-folders/Jenkinsfile.groovy")
            }
        }
    }
}