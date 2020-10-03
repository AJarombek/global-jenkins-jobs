### Overview

This directory contains a Jenkins pipeline job that destroys AWS and Kubernetes infrastructure for the 
`apollo-client-server-prototype` application.

### Files

| Filename                  | Description                                                                             |
|---------------------------|-----------------------------------------------------------------------------------------|
| `jenkinsfile.groovy`      | Jenkinsfile which is executed when the `destroy-infrastructure` pipeline job is run.    |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `destroy-infrastructure` Jenkins job.         |