### Overview

Jenkins jobs for E2E testing the SaintsXCTF web application with Cypress.

### Files

| Filename                  | Description                                                                          |
|---------------------------|--------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `saints-xctf-web-e2e` pipeline job is run.    |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `saints-xctf-web-e2e` Jenkins job.         |
| `pod.yaml`                | Kubernetes Pod YAML document for the `saints-xctf-web-e2e` Jenkins job.              |