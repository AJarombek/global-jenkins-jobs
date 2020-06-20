/**
 * Create a Pipeline Job for initializing the Jenkins server.
 * @author Andrew Jarombek
 * @since 6/20/2020
 */

pipelineJob("init") {
    description("Pipeline Job for initializing the Jenkins server")
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
                scriptPath("bootstrap/init/Jenkinsfile.groovy")
            }
        }
    }
}