### Overview

Jenkins job that creates AWS Lambda layers that are shared amongst applications.

### Files

| Filename                  | Description                                                                              |
|---------------------------|------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `create-lambda-layer` pipeline job is run.        |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `create-lambda-layer` Jenkins job.             |