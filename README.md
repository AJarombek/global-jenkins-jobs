# global-jenkins-jobs

### Overview

Repository for global Jenkins jobs used in my AWS cloud.  The infrastructure for creating the Jenkins server exists in 
the [global-aws-infrastructure](https://github.com/AJarombek/global-aws-infrastructure/tree/master/jenkins) repository.
The Jenkins Job DSL Plugin is used to automate the task of generating Jenkins jobs.  With the Job DSL Plugin, all the 
Jenkins jobs are represented as Groovy scripts.  The names of Job DSL Plugin files can only contain letters, digits, and 
underscores.

### Directories

| Directory Name               | Description                                                                   |
|------------------------------|-------------------------------------------------------------------------------|
| `bake-ami`                   | Jenkins job to back an Amazon Machine Image with Hashicorp Packer.            |
| `cloudformation-serverless`  | Jenkins job to build the serverless prototype.                                |
| `global-aws-infrastructure`  | Jenkins job to run tests on the global AWS infrastructure.                    |
| `kubernetes-prototype`       | Jenkins job to build/destroy AWS infrastructure for the kubernetes prototype. |
| `manual`                     | Jenkins jobs that are manually created instead of through a seed job.         |
| `modules`                    | Groovy modules for creating reusable Job DSL Plugin scripts.                  |
| `react-webpack-seed`         | Jenkins job to build the react-webpack-seed.                                  |
| `saints-xctf-infrastructure` | Jenkins job to run tests on the SaintsXCTF AWS infrastructure.                |
| `single-seed-job`            | Jenkins job creating a seed job that generates a single job.                  |
| `terraform-apply`            | Jenkins job to apply generic infrastructure changes using Terraform.          |
| `terraform-destroy`          | Jenkins job to destroy generic infrastructure using Terraform.                |
| `trigger-seed-job`           | Seed Jenkins job for creating CI trigger jobs.                                |