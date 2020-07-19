### Overview

Jenkins job that destroys MySQL RDS database backup/restore functions for the SaintsXCTF application.

### Files

| Filename                  | Description                                                                              |
|---------------------------|------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `destroy-database-snapshot` pipeline job is run.  |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `destroy-database-snapshot` Jenkins job.       |