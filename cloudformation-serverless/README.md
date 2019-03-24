### Overview

This directory contains the Jenkins pipeline job for creating the Serverless Prototype 
([python-serverless-backend-prototype](https://github.com/AJarombek/python-serverless-backend-prototype)) infrastructure 
with CloudFormation.

### Files

| Filename                            | Description                                                                                      |
|-------------------------------------|--------------------------------------------------------------------------------------------------|
| `jenkinsfile.groovy`                | Jenkinsfile which is executed when the `cloudformation-serverless` pipeline job is run.          |
| `job_dsl.groovy`                    | *Job DSL Plugin* script which creates the `cloudformation-serverless` Jenkins job.               |