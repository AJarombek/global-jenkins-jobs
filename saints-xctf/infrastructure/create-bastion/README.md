### Overview

Jenkins job that creates a bastion host in the `saints-xctf` VPC.

### Files

| Filename                  | Description                                                                              |
|---------------------------|------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `create-bastion` pipeline job is run.            |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `create-bastion` Jenkins job.                 |