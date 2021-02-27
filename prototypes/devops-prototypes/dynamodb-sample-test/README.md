### Overview

This directory contains a Jenkins pipeline job that builds and tests DynamoDB infrastructure.  This is part of the 
`dynamodb` sample code in my `devops-prototypes` repository.

### Files

| Filename             | Description                                                                                   |
|----------------------|-----------------------------------------------------------------------------------------------|
| `jenkinsfile.groovy` | Jenkinsfile which is executed when the `dynamodb-sample-test` pipeline job is run.            |
| `job_dsl.groovy`     | *Job DSL Plugin* script which creates the `dynamodb-sample-test` Jenkins job.                 |
| `pod.yaml`           | Kubernetes YAML config for a Pod with multiple containers.  Runs Go, Python, and Terraform.   |