### Overview

Jenkins jobs which are created on the server when it bootstraps.

### Directories

| Directory Name        | Description                                                                                        |
|-----------------------|----------------------------------------------------------------------------------------------------|
| `init`                | Jenkins job that initializes the Jenkins server with jobs, downloads, folders, and initial builds. |
| `set-folders`         | Jenkins job that sets the folder structure of the server.                                          |
| `single-seed-job`     | Jenkins job creating a seed job that generates a single job.                                       |