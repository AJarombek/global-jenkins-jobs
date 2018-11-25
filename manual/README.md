### Overview

This directory contains all the Jenkins jobs that must be created manually instead of generated through a *Jenkins Job 
DSL Plugin* script.

### Files

| Filename            | Description                                                                                      |
|---------------------|--------------------------------------------------------------------------------------------------|
| `seed-job.groovy`   | *Job DSL Plugin* script which creates a seed job for automating the generation of other jobs     |
| `ssh-key.groovy`    | Pipeline job for creating an SSH private/public key.  This key can be used as GitHub credentials |

### Creation Process

Walk through how to create both these jobs using the Jenkins GUI

**seed-job**

1)

**ssh-key**

1)