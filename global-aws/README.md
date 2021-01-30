### Overview

Jenkins jobs associated with building and testing my cloud's global aws infrastructure.

### Directories

| Directory Name                          | Description                                                                          |
|-----------------------------------------|--------------------------------------------------------------------------------------|
| `cost-detection`                        | Jenkins job to detect unexpected costs for AWS infrastructure.                       |
| `create-lambda-layer`                   | Jenkins job to create global AWS infrastructure for AWS Lambda layers.               |
| `destroy-lambda-layer`                  | Jenkins job to destroy global AWS infrastructure for AWS Lambda layers.              |
| `dockerfiles-push-images`               | Jenkins job which pushes the global Dockerfiles to DockerHub.                        |
| `global-aws-infrastructure-test`        | Jenkins job to run tests on the global AWS infrastructure.                           |
| `global-kubernetes-infrastructure-test` | Jenkins job to run Kubernetes tests on objects built in `global-aws-infrastructure`. |
| `push-jenkins-ecr-docker-image`         | Jenkins job to push the Docker image for Jenkins to an ECR repository.               |