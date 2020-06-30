### Overview

Jenkins jobs for the `graphql-react-prototype`.

### Directories

| Directory Name                     | Description                                                                      |
|------------------------------------|----------------------------------------------------------------------------------|
| `create-ecr-infrastructure`        | Create infrastructure for ECR repositories used by the application.              |
| `create-k8s-infrastructure`        | Create Kubernetes infrastructure for the application.                            |
| `destroy-ecr-infrastructure`       | Destroy infrastructure for ECR repositories used by the application.             |
| `destroy-k8s-infrastructure`       | Destroy Kubernetes infrastructure for the application.                           |
| `graphql-react-prototype-test`     | Jenkins job to run tests in the GraphQL React prototype.                         |
| `push-app-image`                   | Push a Docker image for the hosting the application server to ECR.               |
| `push-base-image`                  | Push a base application Docker image to ECR.                                     |