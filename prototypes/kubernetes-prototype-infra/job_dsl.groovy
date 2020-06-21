/**
 * Create a Pipeline Job for building a Kubernetes Prototype (using EKS) base infrastructure.
 * @author Andrew Jarombek
 * @since 3/24/2019
 */

pipelineJob("prototypes/kubernetes-prototype-infra") {
    description("Pipeline Job for building the Kubernetes Prototype base infrastructure")
    parameters {
        choiceParam(
            "Operation",
            ["create", "destroy"],
            '<span style="font-size: 1.2em">Create or destroy infrastructure with Terraform.</span>'
        )
    }
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("prototypes/kubernetes-prototype-infra/Jenkinsfile.groovy"))
        }
    }
    logRotator {
        daysToKeep(10)
        numToKeep(5)
    }
}
