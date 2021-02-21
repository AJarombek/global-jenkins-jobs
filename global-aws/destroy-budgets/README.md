### Overview

Jenkins job that destroys AWS account budgets and alerts.

### Files

| Filename                  | Description                                                                         |
|---------------------------|-------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `destroy-budgets` pipeline job is run.       |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `destroy-budgets` Jenkins job.            |