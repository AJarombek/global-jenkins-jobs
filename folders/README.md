### Overview

This directory contains Jenkins folders which group Jenkins jobs by repository or application type.  Jenkins folders are 
initialized when the server boots up, not from the seed-job.

### Files

| Filename                  | Description                                                                           |
|---------------------------|---------------------------------------------------------------------------------------|
| `dsl.groovy`              | *Job DSL Plugin* script which creates and configures Jenkins folders.                 |