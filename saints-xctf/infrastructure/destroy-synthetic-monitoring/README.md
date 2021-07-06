### Overview

Jenkins job that destroys AWS CloudWatch Synthetic Monitoring Canary function infrastructure.

### Files

| Filename                  | Description                                                                                |
|---------------------------|--------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `destroy-synthetic-monitoring` pipeline job is run. |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `destroy-synthetic-monitoring` Jenkins job.      |
| `pod.yaml`                | Kubernetes Pod with containers that the Jenkins job runs on.                              |