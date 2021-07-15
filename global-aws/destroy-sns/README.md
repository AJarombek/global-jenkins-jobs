### Overview

Jenkins job that destroys global AWS SNS topics and subscriptions.

### Files

| Filename                  | Description                                                                     |
|---------------------------|---------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `destroy-sns` pipeline job is run.       |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `destroy-sns` Jenkins job.            |