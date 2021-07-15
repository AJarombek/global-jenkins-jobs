### Overview

Jenkins job that creates AWS SNS topics and subscriptions.

### Files

| Filename                  | Description                                                                    |
|---------------------------|--------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `create-sns` pipeline job is run.       |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `create-sns` Jenkins job.            |