/**
 * Create a Pipeline Job for baking AMIs for the Jenkins server
 * @author Andrew Jarombek
 * @since 1/24/2019
 */

pipelineJob("jenkins-bake-ami") {
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("jenkins-bake-ami/Jenkinsfile.groovy"))
        }
    }
    wrappers {
        colorizeOutput()
    }
}
