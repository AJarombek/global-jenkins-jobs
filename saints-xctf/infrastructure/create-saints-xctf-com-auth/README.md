### Overview

Jenkins job that creates the AWS infrastructure for the `auth.saintsxctf.com` domain.

### Files

| Filename                  | Description                                                                                     |
|---------------------------|-------------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `create-saints-xctf-com-auth` pipeline job is run.       |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `create-saints-xctf-com-auth` Jenkins job.            |