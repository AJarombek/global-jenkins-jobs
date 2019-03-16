### Overview

This directory contains the Jenkins pipeline job for destroying cloud infrastructure with Terraform.

### Files

| Filename                    | Description                                                                                      |
|-----------------------------|--------------------------------------------------------------------------------------------------|
| `jenkinsfile.groovy`        | Jenkinsfile which is executed when the `terraform-destroy` pipeline job is run.                  |
| `terraform_destroy.groovy`  | *Job DSL Plugin* script which creates the `terraform-destroy` Jenkins job.                       |