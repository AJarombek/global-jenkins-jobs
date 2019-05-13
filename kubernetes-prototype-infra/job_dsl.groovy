/**
 * Create a Pipeline Job for building a Kubernetes Prototype (using EKS) base infrastructure.
 * @author Andrew Jarombek
 * @since 3/24/2019
 */

pipelineJob("kubernetes-prototype-infra") {
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
            script(readFileFromWorkspace("kubernetes-prototype/Jenkinsfile.groovy"))
        }
    }
}
