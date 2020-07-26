### Overview

Jenkins job that creates the AWS infrastructure for the `api.saintsxctf.com` domain (the API).

### Files

| Filename                  | Description                                                                                |
|---------------------------|--------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `create-saints-xctf-com-api` pipeline job is run.   |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `create-saints-xctf-com-api` Jenkins job.        |