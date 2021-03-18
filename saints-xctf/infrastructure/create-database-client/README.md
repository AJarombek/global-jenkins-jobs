### Overview

Jenkins job that creates Kubernetes and AWS infrastructure for the SaintsXCTF database client (`db.saintsxctf.com`).

### Files

| Filename                  | Description                                                                                |
|---------------------------|--------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `create-database-client` pipeline job is run.       |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `create-database-client` Jenkins job.            |