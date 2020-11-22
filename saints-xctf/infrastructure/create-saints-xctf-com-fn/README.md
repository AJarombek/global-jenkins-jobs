### Overview

Jenkins job that creates the AWS infrastructure for the `fn.saintsxctf.com` domain.

> [Job on jenkins.jarombek.io](https://jenkins.jarombek.io/job/saints-xctf/job/infrastructure/job/create-saints-xctf-com-fn/)

### Files

| Filename                  | Description                                                                                   |
|---------------------------|-----------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `create-saints-xctf-com-fn` pipeline job is run.       |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `create-saints-xctf-com-fn` Jenkins job.            |