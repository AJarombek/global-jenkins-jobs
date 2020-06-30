### Overview

This directory contains a Jenkins pipeline job that creates Kubernetes resources in an EKS cluster for the 
`graphql-react-prototype` application.

### Files

| Filename                  | Description                                                                                |
|---------------------------|--------------------------------------------------------------------------------------------|
| `jenkinsfile.groovy`      | Jenkinsfile which is executed when the `create-k8s-infrastructure` pipeline job is run.    |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `create-k8s-infrastructure` Jenkins job.         |