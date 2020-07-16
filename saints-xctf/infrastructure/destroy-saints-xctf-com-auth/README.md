### Overview

Jenkins job that destroys the AWS infrastructure for the `auth.saintsxctf.com` domain.

### Files

| Filename                  | Description                                                                                      |
|---------------------------|--------------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `destroy-saints-xctf-com-auth` pipeline job is run.       |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `destroy-saints-xctf-com-auth` Jenkins job.            |