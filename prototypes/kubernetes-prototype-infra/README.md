### Overview

This directory contains the Jenkins pipeline job for creating or destroying the Kubernetes prototype infrastructure.

### Files

| Filename                  | Description                                                                                      |
|---------------------------|--------------------------------------------------------------------------------------------------|
| `jenkinsfile.groovy`      | Jenkinsfile which is executed when the `kubernetes-prototype-infra` pipeline job is run.         |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `kubernetes-prototype-infra` Jenkins job.              |