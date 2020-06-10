### Overview

Jenkins job for pushing a Docker image for a Jenkins server (which at the moment is hosted on an ECS cluster) to an ECR 
repository.

### Files

| Filename                  | Description                                                                                      |
|---------------------------|--------------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `push-jenkins-ecr-docker-image` pipeline job is run.      |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `push-jenkins-ecr-docker-image` Jenkins job.           |