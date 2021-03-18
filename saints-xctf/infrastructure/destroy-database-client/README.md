### Overview

Jenkins job that destroys Kubernetes and AWS infrastructure for the SaintsXCTF database client (`db.saintsxctf.com`).

### Files

| Filename                  | Description                                                                                |
|---------------------------|--------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `destroy-database-client` pipeline job is run.      |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `destroy-database-client` Jenkins job.           |