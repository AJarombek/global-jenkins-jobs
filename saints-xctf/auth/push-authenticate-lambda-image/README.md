### Overview

Jenkins job that pushes a Docker image to DockerHub that helps create an AWS Lambda zip file.  The resulting AWS Lambda 
function is `SaintsXCTFAuthenticate`.

### Files

| Filename                  | Description                                                                                  |
|---------------------------|----------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `push-authenticate-lambda-image` pipeline job is run. |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `push-authenticate-lambda-image` Jenkins job.      |