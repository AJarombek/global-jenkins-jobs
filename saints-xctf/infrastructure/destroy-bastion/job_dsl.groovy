/**
 * Destroy a bastion host in the SaintsXCTF VPC.
 * @author Andrew Jarombek
 * @since 7/19/2020
 */

pipelineJob("saints-xctf/infrastructure/destroy-bastion") {
    description("Pipeline Job for destroying s bastion host in the SaintsXCTF VPC.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/infrastructure/destroy-bastion/Jenkinsfile.groovy"))
        }
    }
}
