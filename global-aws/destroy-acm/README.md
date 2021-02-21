### Overview

Jenkins job that destroys AWS ACM certificates.

### Files

| Filename                  | Description                                                                     |
|---------------------------|---------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `destroy-acm` pipeline job is run.       |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `destroy-acm` Jenkins job.            |