/**
 * Create a Pipeline Job for baking AMIs
 * @author Andrew Jarombek
 * @since 1/24/2019
 */

pipelineJob("devops-jobs/bake-ami") {
    description("Pipeline Job for baking AMIs")
    parameters {
        stringParam('repository', '', 'Repository containing a Packer template to build an AMI')
        stringParam('branch', 'master', 'Branch in the repository containing a Packer template to build an AMI from')
        stringParam('path', '', 'Directory path to the Packer template')
        stringParam('filename', '', 'Packer template filename')
    }
    concurrentBuild(true)
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("bake-ami/Jenkinsfile.groovy"))
        }
    }
    logRotator {
        daysToKeep(10)
        numToKeep(5)
    }
}
