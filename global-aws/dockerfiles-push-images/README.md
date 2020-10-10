### Overview

Jenkins job for pushing Docker images used throughout my infrastructure to DockerHub.

### Files

| Filename                  | Description                                                                                |
|---------------------------|--------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `dockerfiles-push-images` pipeline job is run.      |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `dockerfiles-push-images` Jenkins job.           |