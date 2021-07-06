### Overview

Jenkins job that creates AWS CloudWatch Synthetic Monitoring Canary function infrastructure.

### Files

| Filename                  | Description                                                                               |
|---------------------------|-------------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `create-synthetic-monitoring` pipeline job is run. |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `create-synthetic-monitoring` Jenkins job.      |
| `pod.yaml`                | Kubernetes Pod with containers that the Jenkins job runs on.                              |