### Overview

Jenkins job that destroys AWS CloudTrail trails and S3 buckets to hold CloudTrail logs.

### Files

| Filename                  | Description                                                                             |
|---------------------------|-----------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `destroy-cloud-trail` pipeline job is run.       |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `destroy-cloud-trail` Jenkins job.            |