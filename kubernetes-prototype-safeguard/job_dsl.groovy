/**
 * Create a Pipeline Job for safeguarding the EKS cluster from running too long.  In its current state, EKS is
 * expensive ($0.20 per hour) and it can be easy to forget to destroy the infrastructure after working on it.
 * @author Andrew Jarombek
 * @since 5/23/2019
 */

pipelineJob("kubernetes-prototype/kubernetes-prototype-safeguard") {
    description("Pipeline Job for safeguarding the Kubernetes Prototype EKS cluster")
    triggers {
        // This job will run every morning when the Jenkins server is up
        scm('15 11 * * *')
    }
    concurrentBuild(false)
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("kubernetes-prototype-safeguard/Jenkinsfile.groovy"))
        }
    }
}
