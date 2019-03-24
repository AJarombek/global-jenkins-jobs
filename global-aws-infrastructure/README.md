### Overview

This directory contains the Jenkins pipeline job for running cloud infrastructure tests for the global AWS 
infrastructure.  This infrastructure is found in the 
[global-aws-infrastructure](https://github.com/AJarombek/global-aws-infrastructure) repository.

### Files

| Filename                  | Description                                                                                      |
|---------------------------|--------------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `global-aws-infrastructure` pipeline job is run.          |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `global-aws-infrastructure` Jenkins job.               |