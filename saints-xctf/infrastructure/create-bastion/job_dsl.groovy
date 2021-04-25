/**
 * Create a bastion host in the SaintsXCTF VPC.
 * @author Andrew Jarombek
 * @since 7/19/2020
 */

pipelineJob("saints-xctf/infrastructure/create-bastion") {
    description("Pipeline Job for creating a bastion host in the SaintsXCTF VPC.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/infrastructure/create-bastion/Jenkinsfile.groovy"))
        }
    }
    parameters {
        booleanParam('autoApply', true, "Whether the Terraform infrastructure should be automatically approved.")
    }
}
