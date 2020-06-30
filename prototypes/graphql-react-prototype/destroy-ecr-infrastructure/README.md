### Overview

This directory contains a Jenkins pipeline job that destroys ECR repositories in AWS for the `graphql-react-prototype` 
application.

### Files

| Filename                  | Description                                                                                |
|---------------------------|--------------------------------------------------------------------------------------------|
| `jenkinsfile.groovy`      | Jenkinsfile which is executed when the `destroy-ecr-infrastructure` pipeline job is run.   |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `destroy-ecr-infrastructure` Jenkins job.        |