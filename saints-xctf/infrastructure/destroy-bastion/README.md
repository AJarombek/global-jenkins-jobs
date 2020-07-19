### Overview

Jenkins job that destroys a bastion host in the `saints-xctf` VPC.

### Files

| Filename                  | Description                                                                              |
|---------------------------|------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `destroy-bastion` pipeline job is run.            |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `destroy-bastion` Jenkins job.                 |