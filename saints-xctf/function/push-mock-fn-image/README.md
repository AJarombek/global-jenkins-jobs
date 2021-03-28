### Overview

This directory contains a Jenkins pipeline job that pushes a `mock-saints-xctf-fn` Docker image to Dockerhub for the
`saints-xctf-function` application.

### Files

| Filename                  | Description                                                                         |
|---------------------------|-------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `push-mock-fn-image` pipeline job is run.    |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `push-mock-fn-image` Jenkins job.         |