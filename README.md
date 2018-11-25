# global-jenkins-jobs

### Overview

Repository for global Jenkins jobs used in my AWS cloud.  The infrastructure for creating the Jenkins server exists in 
the [global-aws-infrastructure](https://github.com/AJarombek/global-aws-infrastructure/tree/master/jenkins) repository.

### Directories

| Directory Name      | Description                                                                 |
|---------------------|-----------------------------------------------------------------------------|
| `manual`            | Jenkins jobs that are manually created instead of through a seed job.       |
| `terraform-apply`   | Jenkins job to apply generic infrastructure changes using Terraform.        |
| `terraform-destroy` | Jenkins job to destroy generic infrastructure using Terraform.              |