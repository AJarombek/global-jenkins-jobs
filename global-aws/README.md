### Overview

Jenkins jobs associated with building and testing my cloud's global aws infrastructure.

### Directories

| Directory Name                          | Description                                                                          |
|-----------------------------------------|--------------------------------------------------------------------------------------|
| `create-lambda`                         | Jenkins job to create global AWS infrastructure for AWS Lambda functions.            |
| `create-lambda-layer`                   | Jenkins job to create global AWS infrastructure for AWS Lambda layers.               |
| `destroy-lambda`                        | Jenkins job to destroy global AWS infrastructure for AWS Lambda functions.           |
| `destroy-lambda-layer`                  | Jenkins job to destroy global AWS infrastructure for AWS Lambda layers.              |
| `dockerfiles-push-images`               | Jenkins job which pushes the global Dockerfiles to DockerHub.                        |
| `push-jenkins-ecr-docker-image`         | Jenkins job to push the Docker image for Jenkins to an ECR repository.               |