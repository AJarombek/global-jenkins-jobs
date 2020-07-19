### Overview

Jenkins job that destroys a MySQL RDS database for the SaintsXCTF application.

### Files

| Filename                  | Description                                                                              |
|---------------------------|------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `destroy-database` pipeline job is run.           |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `destroy-database` Jenkins job.                |