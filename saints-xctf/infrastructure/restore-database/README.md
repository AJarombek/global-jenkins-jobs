### Overview

Jenkins job that restores a database from a backup.

### Files

| Filename                  | Description                                                                              |
|---------------------------|------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `restore-database` pipeline job is run.           |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `restore-database` Jenkins job.                |