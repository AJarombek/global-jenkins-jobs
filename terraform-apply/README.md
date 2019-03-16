### Overview

This directory contains the Jenkins pipeline job for creating cloud infrastructure with Terraform.

### Files

| Filename                  | Description                                                                                      |
|---------------------------|--------------------------------------------------------------------------------------------------|
| `jenkinsfile.groovy`      | Jenkinsfile which is executed when the `terraform-apply` pipeline job is run.                    |
| `terraform_apply.groovy`  | *Job DSL Plugin* script which creates the `terraform-apply` Jenkins job.                         |