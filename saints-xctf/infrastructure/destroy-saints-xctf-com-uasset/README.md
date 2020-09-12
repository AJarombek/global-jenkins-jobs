### Overview

Jenkins job that destroys a user asset S3 bucket for the SaintsXCTF application.

### Files

| Filename                  | Description                                                                                    |
|---------------------------|------------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `destroy-saints-xctf-com-uasset` pipeline job is run.   |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `destroy-saints-xctf-com-uasset` Jenkins job.        |