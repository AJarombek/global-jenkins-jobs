### Overview

This directory contains a Jenkins pipeline job that pushes Docker images to Dockerhub for the
`apollo-client-server-prototype` application.  The images are for the client, server, and database.

### Files

| Filename                  | Description                                                                           |
|---------------------------|---------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `push-images` pipeline job is run.             |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `push-images` Jenkins job.                  |