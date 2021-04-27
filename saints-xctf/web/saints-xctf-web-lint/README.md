### Overview

Jenkins jobs for linting TypeScript code in the SaintsXCTF web application.

### Files

| Filename                  | Description                                                                           |
|---------------------------|---------------------------------------------------------------------------------------|
| `Jenkinsfile.groovy`      | Jenkinsfile which is executed when the `saints-xctf-web-lint` pipeline job is run.    |
| `job_dsl.groovy`          | *Job DSL Plugin* script which creates the `saints-xctf-web-lint` Jenkins job.         |
| `pod.yaml`                | Kubernetes Pod YAML document for the `saints-xctf-web-lint` Jenkins job.              |