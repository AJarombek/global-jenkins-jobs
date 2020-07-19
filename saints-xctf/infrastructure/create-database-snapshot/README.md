### Overview

Jenkins job that creates MySQL RDS database backup/restore functions for the SaintsXCTF application.

### Files

| Filename                  | Description                                                                              |
|---------------------------|------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `create-database-snapshot` pipeline job is run.   |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `create-database-snapshot` Jenkins job.        |