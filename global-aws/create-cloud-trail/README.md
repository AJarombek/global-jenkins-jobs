### Overview

Jenkins job that creates AWS CloudTrail trails and S3 buckets to hold CloudTrail logs.

### Files

| Filename                  | Description                                                                        |
|---------------------------|------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `create-cloud-trail` pipeline job is run.   |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `create-cloud-trail` Jenkins job.        |