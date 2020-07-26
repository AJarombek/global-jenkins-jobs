### Overview

Jenkins job that destroys AWS infrastructure for the `saintsxctf.com` domain (the web application).

### Files

| Filename                  | Description                                                                                |
|---------------------------|--------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `destroy-saints-xctf-com` pipeline job is run.      |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `destroy-saints-xctf-com` Jenkins job.           |
| `pod.yaml`                | Kubernetes Pod with containers that the Jenkins job runs on.                               |