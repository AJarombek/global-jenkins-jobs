/**
 * Create a Pipeline Job for testing the saints-xctf-infrastructure project.  Runs python scripts to test AWS
 * infrastructure on a schedule.  Each environment gets its own job.
 * @author Andrew Jarombek
 * @since 3/16/2019
 */

pipelineJob("saints-xctf-infrastructure/saints-xctf-rds-snapshot-lambda") {
    description("Pipeline Job for deploying an AWS Lambda function that creates a RDS snapshot")
    parameters {
        stringParam(
            'branch',
            'master',
            'Branch in the saints-xctf-infrastructure repository to retrieve the lambda function from'
        )
        stringParam(
            'env',
            'prod',
            'Environment in which the lambda function will be created.'
        )
    }
    concurrentBuild(true)
    definition {
        cps {
            sandbox()
            script(readFileFromWorkspace("saints-xctf-rds-snapshot-lambda/Jenkinsfile.groovy"))
        }
    }
}