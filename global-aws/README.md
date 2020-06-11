### Overview

Jenkins jobs associated with building and testing my cloud's global aws infrastructure.

### Directories

| Directory Name                     | Description                                                                      |
|------------------------------------|----------------------------------------------------------------------------------|
| `create-jenkins-ecs`               | Jenkins jobs to create AWS Infrastructure for Jenkins ECS.                       |
| `destroy-jenkins-ecs`              | Jenkins jobs to destroy AWS Infrastructure for Jenkins ECS.                      |
| `global-aws-infrastructure-test`   | Jenkins job to run tests on the global AWS infrastructure.                       |
| `push-jenkins-ecr-docker-image`    | Jenkins job to push the Docker image for Jenkins to an ECR repository.           |