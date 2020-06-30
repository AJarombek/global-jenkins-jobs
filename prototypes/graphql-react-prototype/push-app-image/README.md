### Overview

This directory contains a Jenkins pipeline job that pushes a Docker image to ECR.  The `graphql-react-prototype-app` 
Docker image sets up a web server for hosting the application.

### Files

| Filename                  | Description                                                                             |
|---------------------------|-----------------------------------------------------------------------------------------|
| `jenkinsfile.groovy`      | Jenkinsfile which is executed when the `push-app-image` pipeline job is run.            |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `push-app-image` Jenkins job.                 |