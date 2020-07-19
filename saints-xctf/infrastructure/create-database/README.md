### Overview

Jenkins job that creates a MySQL RDS database for the SaintsXCTF application.

### Files

| Filename                  | Description                                                                              |
|---------------------------|------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `create-database` pipeline job is run.            |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `create-database` Jenkins job.                 |