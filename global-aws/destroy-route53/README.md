### Overview

Jenkins job that destroys global AWS Route53 records and health checks.

### Files

| Filename                  | Description                                                                         |
|---------------------------|-------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `destroy-route53` pipeline job is run.       |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `destroy-route53` Jenkins job.            |