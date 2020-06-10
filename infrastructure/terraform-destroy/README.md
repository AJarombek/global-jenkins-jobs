### Overview

This directory contains the Jenkins pipeline job for destroying cloud infrastructure with Terraform.

### Files

| Filename                    | Description                                                                                      |
|-----------------------------|--------------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`        | Jenkinsfile which is executed when the `terraform-destroy` pipeline job is run.                  |
| `job_dsl.groovy`            | *Job DSL Plugin* script which creates the `terraform-destroy` Jenkins job.                       |