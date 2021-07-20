### Overview

Jenkins job that destroys AWS Lambda functions.

### Files

| Filename                  | Description                                                                        |
|---------------------------|------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `destroy-lambda` pipeline job is run.       |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `destroy-lambda` Jenkins job.            |
| `pod.yaml`                | Kubernetes YAML config for a Pod with a container for Terraform.                   |