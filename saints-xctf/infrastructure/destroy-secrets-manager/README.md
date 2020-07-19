### Overview

Jenkins job that destroys secrets in Secrets Manager for the SaintsXCTF application.

### Files

| Filename                  | Description                                                                             |
|---------------------------|-----------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `destroy-secrets-manager` pipeline job is run.   |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `destroy-secrets-manager` Jenkins job.        |