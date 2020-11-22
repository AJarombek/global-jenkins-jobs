### Overview

Jenkins job that destroys the AWS infrastructure for the `fn.saintsxctf.com` domain.

> [Job on jenkins.jarombek.io](https://jenkins.jarombek.io/job/saints-xctf/job/infrastructure/job/destroy-saints-xctf-com-fn/)

### Files

| Filename                  | Description                                                                                   |
|---------------------------|-----------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `destroy-saints-xctf-com-fn` pipeline job is run.      |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `destroy-saints-xctf-com-fn` Jenkins job.           |