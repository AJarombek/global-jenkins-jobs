### Overview

This directory contains a Jenkins pipeline job that creates ACM certificates in AWS for the `graphql-react-prototype` 
application.

### Files

| Filename                  | Description                                                                                |
|---------------------------|--------------------------------------------------------------------------------------------|
| `jenkinsfile.groovy`      | Jenkinsfile which is executed when the `create-acm-infrastructure` pipeline job is run.    |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `create-acm-infrastructure` Jenkins job.         |