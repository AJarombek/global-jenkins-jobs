### Overview

Jenkins job that destroys Kubernetes `Ingress` infrastructure for SaintsXCTF.

> [Job on jenkins.jarombek.io](https://jenkins.jarombek.io/job/saints-xctf/job/infrastructure/job/destroy-saints-xctf-com-ingress/)

### Files

| Filename                  | Description                                                                                    |
|---------------------------|------------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `destroy-saints-xctf-com-ingress` pipeline job is run.  |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `destroy-saints-xctf-com-ingress` Jenkins job.       |