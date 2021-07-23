### Overview

Jenkins job that destroys AWS S3 global jarombek assets.

### Files

| Filename                  | Description                                                                    |
|---------------------------|--------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `destroy-s3` pipeline job is run.       |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `destroy-s3` Jenkins job.            |
| `pod.yaml`                | Kubernetes YAML config for a Pod with a container for Terraform.               |