### Overview

This directory contains a Jenkins pipeline job that pushes a Docker image to ECR.  The `graphql-react-prototype-base` 
Docker image sets up the environment to test and build the application.

### Files

| Filename                  | Description                                                                              |
|---------------------------|------------------------------------------------------------------------------------------|
| `jenkinsfile.groovy`      | Jenkinsfile which is executed when the `push-base-image` pipeline job is run.            |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `push-base-image` Jenkins job.                 |