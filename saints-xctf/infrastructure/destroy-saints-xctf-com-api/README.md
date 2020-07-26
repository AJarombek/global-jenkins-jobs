### Overview

Jenkins job that destroys AWS infrastructure for the `api.saintsxctf.com` domain (the API).

### Files

| Filename                  | Description                                                                                |
|---------------------------|--------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `destroy-saints-xctf-com-api` pipeline job is run.  |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `destroy-saints-xctf-com-api` Jenkins job.       |
| `pod.yaml`                | Kubernetes Pod with containers that the Jenkins job runs on.                               |