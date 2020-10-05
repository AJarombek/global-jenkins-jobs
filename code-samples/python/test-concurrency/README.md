### Overview

This directory contains a Jenkins pipeline job that runs unit tests for Python concurrency code samples.  The 
concurrency code utilizes futures, `asyncio`, and `aiohttp`.

### Files

| Filename                  | Description                                                                           |
|---------------------------|---------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `test-concurrency` pipeline job is run.        |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `test-concurrency` Jenkins job.             |