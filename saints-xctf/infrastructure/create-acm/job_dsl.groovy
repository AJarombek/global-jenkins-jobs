/**
 * Create ACM certificates for the SaintsXCTF application.
 * @author Andrew Jarombek
 * @since 7/18/2020
 */

pipelineJob("saints-xctf/infrastructure/create-acm") {
    description("Pipeline Job for creating ACM certificates for the SaintsXCTF application.")
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf/infrastructure/create-acm/Jenkinsfile.groovy"))
        }
    }
    parameters {
        booleanParam('autoApply', true, "Whether the Terraform infrastructure should be automatically approved.")
        choiceParam(
            'cert',
            [
                '*.asset.saintsxctf.com',
                '*.auth.saintsxctf.com',
                '*.dev.saintsxctf.com',
                '*.fn.saintsxctf.com',
                '*.saintsxctf.com, saintsxctf.com',
                '*.uasset.saintsxctf.com'
            ],
            'Certificate(s) to create.'
        )
    }
}
