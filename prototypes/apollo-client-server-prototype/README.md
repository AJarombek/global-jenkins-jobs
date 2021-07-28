### Overview

Jenkins jobs for the `apollo-client-server-prototype`.

### Directories

| Directory Name                     | Description                                                                      |
|------------------------------------|----------------------------------------------------------------------------------|
| `create-infrastructure`            | Create AWS and Kubernetes infrastructure for the application.                    |
| `destroy-infrastructure`           | Destroy AWS and Kubernetes infrastructure for the application.                   |
| `push-images`                      | Push client, server, and database Docker images to Dockerhub.                    |
| `test-client`                      | Run unit tests and end to end tests for the client.                              |
| `test-server`                      | Run integration tests for the server.                                            |