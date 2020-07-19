### Overview

Jenkins job that creates secrets in Secrets Manager for the SaintsXCTF application.

### Files

| Filename                  | Description                                                                            |
|---------------------------|----------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `create-secrets-manager` pipeline job is run.   |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `create-secrets-manager` Jenkins job.        |