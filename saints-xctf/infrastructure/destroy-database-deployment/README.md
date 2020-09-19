### Overview

Jenkins job that destroys a database deployment AWS Lambda Function for the SaintsXCTF application.

### Files

| Filename                  | Description                                                                                |
|---------------------------|--------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `destroy-database-deployment` pipeline job is run.  |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `destroy-database-deployment` Jenkins job.       |