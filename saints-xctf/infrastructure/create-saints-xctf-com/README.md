### Overview

Jenkins job that creates the AWS infrastructure for the `saintsxctf.com` domain (the web application).

### Files

| Filename                  | Description                                                                                |
|---------------------------|--------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `create-saints-xctf-com` pipeline job is run.       |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `create-saints-xctf-com` Jenkins job.            |