### Overview

Jenkins job that creates an asset S3 bucket for the SaintsXCTF application.

### Files

| Filename                  | Description                                                                                  |
|---------------------------|----------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `create-saints-xctf-com-asset` pipeline job is run.   |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `create-saints-xctf-com-asset` Jenkins job.        |