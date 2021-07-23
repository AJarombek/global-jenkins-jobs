### Overview

Jenkins job that creates AWS S3 bucket for global jarombek assets.

### Files

| Filename                  | Description                                                                   |
|---------------------------|-------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `create-s3` pipeline job is run.       |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `create-s3` Jenkins job.            |
| `pod.yaml`                | Kubernetes YAML config for a Pod with a container for Terraform.              |