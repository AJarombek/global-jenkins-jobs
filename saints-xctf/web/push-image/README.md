### Overview

This directory contains a Jenkins pipeline job that pushes a Docker image to ECR.  The `saints-xctf-web` Docker 
image runs the React application behind a Nginx reverse proxy.

### Files

| Filename                  | Description                                                                         |
|---------------------------|-------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `push-image` pipeline job is run.            |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `push-image` Jenkins job.                 |