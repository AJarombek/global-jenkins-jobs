### Overview

Jenkins job that creates AWS account budgets and alerts.

### Files

| Filename                  | Description                                                                        |
|---------------------------|------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `create-budgets` pipeline job is run.       |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `create-budgets` Jenkins job.            |