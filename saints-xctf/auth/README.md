### Overview

Jenkins jobs for the SaintsXCTF Auth Lambda/API Gateway functions/infrastructure.

### Directories

| Directory Name                     | Description                                                                        |
|------------------------------------|------------------------------------------------------------------------------------|
| `push-authenticate-lambda-image`   | Push the `auth-saints-xctf-com-authenticate` AWS Lambda Docker image to DockerHub. |
| `push-authorizer-lambda-image`     | Push the `auth-saints-xctf-com-authorizer` AWS Lambda Docker image to DockerHub.   |
| `push-rotate-lambda-image`         | Push the `auth-saints-xctf-com-rotate` AWS Lambda Docker image to DockerHub.       |
| `push-token-lambda-image`          | Push the `auth-saints-xctf-com-token` AWS Lambda Docker image to DockerHub.        |
| `saints-xctf-auth-test`            | Run tests which call the Token and Authenticate Lambda functions via API Gateway.  |