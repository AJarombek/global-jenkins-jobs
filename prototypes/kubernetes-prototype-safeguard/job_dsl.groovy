/**
 * Create a Pipeline Job for safeguarding the EKS cluster from running too long.  In its current state, EKS is
 * expensive ($0.20 per hour) and it can be easy to forget to destroy the infrastructure after working on it.
 * @author Andrew Jarombek
 * @since 5/23/2019
 */

pipelineJob("prototypes/kubernetes-prototype-safeguard") {
    description("Pipeline Job for safeguarding the Kubernetes Prototype EKS cluster")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("prototypes/kubernetes-prototype-safeguard/Jenkinsfile.groovy"))
        }
    }
    logRotator {
        daysToKeep(10)
        numToKeep(5)
    }
}
