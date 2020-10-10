### Overview

Jenkins jobs for executing unit tests for my SaintsXCTF V2 API written in Python and Flask.

### Files

| Filename                  | Description                                                                           |
|---------------------------|---------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `saints-xctf-api-test` pipeline job is run.    |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `saints-xctf-api-test` Jenkins job.         |
| `pod.yaml`                | Kubernetes pods used by the Jenkins job.                                              |