### Overview

This directory contains the Jenkins pipeline job for running Unit tests defined in the Kubernetes prototype.

### Files

| Filename                  | Description                                                                                      |
|---------------------------|--------------------------------------------------------------------------------------------------|
| `jenkinsfile.groovy`      | Jenkinsfile which is executed when the `kubernetes-prototype-ci` pipeline job is run.            |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `kubernetes-prototype-ci` Jenkins job.                 |