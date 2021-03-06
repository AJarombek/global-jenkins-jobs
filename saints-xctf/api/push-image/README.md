### Overview

This directory contains a Jenkins pipeline job that pushes a Docker image to ECR.  The `saints-xctf-api-flask` Docker 
image runs the Python/Flask API.  The `saints-xctf-api-nginx` is a reverse proxy to the Flask API.

### Files

| Filename                  | Description                                                                         |
|---------------------------|-------------------------------------------------------------------------------------|
| `jenkinsfile.groovy`      | Jenkinsfile which is executed when the `push-image` pipeline job is run.            |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `push-image` Jenkins job.                 |