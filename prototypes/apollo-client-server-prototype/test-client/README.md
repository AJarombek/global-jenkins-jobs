### Overview

This directory contains a Jenkins pipeline job that tests the Apollo Client React codebase in the 
`apollo-client-server-prototype` application.

### Files

| Filename                  | Description                                                                           |
|---------------------------|---------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `test-client` pipeline job is run.             |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `test-client` Jenkins job.                  |