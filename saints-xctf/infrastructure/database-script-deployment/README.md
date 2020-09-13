### Overview

Jenkins job that deploys SQL scripts to a MySQL RDS database for SaintsXCTF.

### Files

| Filename                  | Description                                                                              |
|---------------------------|------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `database-script-deployment` pipeline job is run. |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `database-script-deployment` Jenkins job.      |