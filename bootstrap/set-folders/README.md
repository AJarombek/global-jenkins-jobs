### Overview

A Jenkins pipeline job for setting the folder structure of the server.  This job is created when the server boots up, 
and runs as part of the `init` job.

### Files

| Filename                  | Description                                                                           |
|---------------------------|---------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `set-folders` pipeline job is run.             |
| `dsl.groovy`              | *Job DSL Plugin* script which creates the `set-folders` Jenkins job.                  |