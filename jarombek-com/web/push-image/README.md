### Overview

This directory contains a Jenkins pipeline job that pushes Docker images to DockerHub.  There are two Docker images 
handled by this pipeline: `jarombek-com` and `jarombek-com-database`.

### Files

| Filename                  | Description                                                                         |
|---------------------------|-------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `push-image` pipeline job is run.            |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `push-image` Jenkins job.                 |