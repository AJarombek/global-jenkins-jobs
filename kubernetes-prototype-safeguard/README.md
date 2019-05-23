### Overview

This directory contains the Jenkins pipeline job for destroying the EKS cluster if its still running in the morning.

### Files

| Filename                  | Description                                                                                      |
|---------------------------|--------------------------------------------------------------------------------------------------|
| `jenkinsfile.groovy`      | Jenkinsfile which is executed when the `kubernetes-prototype-safeguard` pipeline job is run.     |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `kubernetes-prototype-safeguard` Jenkins job.          |