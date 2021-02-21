### Overview

This directory contains a Jenkins pipeline job that runs Kubernetes tests in the `global-aws-infrastructure` repository.

### Files

| Filename             | Description                                                                                          |
|----------------------|------------------------------------------------------------------------------------------------------|
| `jenkinsfile.groovy` | Jenkinsfile which is executed when the `global-kubernetes-infrastructure-test` pipeline job is run.  |
| `job_dsl.groovy`     | *Job DSL Plugin* script which creates the `global-kubernetes-infrastructure-test` Jenkins job.       |
| `pod.yaml`           | Kubernetes YAML config for a Pod used to run Go code.                                                |