### Overview

This directory contains the Jenkins pipeline job for detecting unexpected AWS costs (3-day rolling average).

### Files

| Filename                  | Description                                                                           |
|---------------------------|---------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `cost-detection` pipeline job is run.          |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `cost-detection` Jenkins job.               |