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

1) From the main Jenkins UI page, click on *New Item*.
2) Name the new item `seed` and select *Freestyle project*.  Click *OK*.
3) Navigate to *Build* and select *Add build step* > *Process Job DSLs*.  Click *Use the provided DSL script* to add the 
Groovy file manually.  Copy and paste the `seed-job.groovy` script into the textarea.  Click *Save*.

**ssh-key**

1) From the main Jenkins UI page, click on *New Item*.
2) Name the new item `ssh-key` and select *Pipeline*.  Click *OK*.
3) Navigate to *Pipeline*.  Copy and paste the `ssh-key.groovy` script into the *Script* textarea.  Click *Save*.