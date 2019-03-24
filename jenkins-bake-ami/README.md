### Overview

This directory contains the Jenkins pipeline job for baking AMIs for the jenkins server.

### Files

| Filename                  | Description                                                                                      |
|---------------------------|--------------------------------------------------------------------------------------------------|
| `jenkinsfile.groovy`      | Jenkinsfile which is executed when the `jenkins-bake-ami` pipeline job is run.                   |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `jenkins-bake-ami` Jenkins job.                        |