### Overview

Jenkins job that creates an S3 bucket holding MySQL RDS database backup files for the SaintsXCTF application.

### Files

| Filename                  | Description                                                                              |
|---------------------------|------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `create-database-backup` pipeline job is run.     |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `create-database-backup` Jenkins job.          |