### Overview

This directory contains a Jenkins Job DSL Script which generates a freestyle seed job for a single job.  This seed job 
is created when the server first boots up.

### Files

| Filename                  | Description                                                                                      |
|---------------------------|--------------------------------------------------------------------------------------------------|
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `single-seed-job` Jenkins job.                         |