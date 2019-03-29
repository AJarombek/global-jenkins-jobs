/**
 * Create a Pipeline Job for baking AMIs for the Jenkins server
 * @author Andrew Jarombek
 * @since 1/24/2019
 */

pipelineJob("bake-ami") {
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("bake-ami/Jenkinsfile.groovy"))
        }
    }
    wrappers {
        colorizeOutput()
    }
}
