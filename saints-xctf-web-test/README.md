### Overview

Jenkins jobs for executing unit tests, snapshot tests, and integration tests for my SaintsXCTF web application.

### Files

| Filename                  | Description                                                                           |
|---------------------------|---------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `saints-xctf-web` pipeline job is run.         |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `saints-xctf-web` Jenkins job.              |