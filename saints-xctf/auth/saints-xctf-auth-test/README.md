### Overview

Jenkins job that runs jest tests for the `SaintsXCTFToken` and `SaintsXCTFAuthenticate` lambda functions.  The functions 
are called via API Gateway.

### Files

| Filename                  | Description                                                                                 |
|---------------------------|---------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `saints-xctf-auth-test` pipeline job is run.         |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `saints-xctf-auth-test` Jenkins job.              |