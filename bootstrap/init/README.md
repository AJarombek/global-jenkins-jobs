### Overview

A Jenkins pipeline job for initializing the server with jobs, dependencies, folders, and initial builds.  This job is 
created when the server boots up, and needs to be built manually.

### Files

| Filename                  | Description                                                                    |
|---------------------------|--------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `init` pipeline job is run.             |
| `dsl.groovy`              | *Job DSL Plugin* script which creates the `init` Jenkins job.                  |