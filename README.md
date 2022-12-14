# global-jenkins-jobs

![Maintained Label](https://img.shields.io/badge/Maintained-Yes-brightgreen?style=for-the-badge)

### Overview

Repository for global Jenkins jobs used in my AWS cloud.  The infrastructure for creating the Jenkins server exists in 
the [global-aws-infrastructure](https://github.com/AJarombek/global-aws-infrastructure/tree/master/jenkins) repository.
The Jenkins Job DSL Plugin is used to automate the task of generating Jenkins jobs.  With the Job DSL Plugin, all the 
Jenkins jobs are represented as Groovy scripts.  The names of Job DSL Plugin files can only contain letters, digits, and 
underscores.

### Commands

**Script Console**

> Remove all pending builds from the queue.

```groovy
Jenkins.instance.queue.clear()
```

### Directories

| Directory Name                     | Description                                                                      |
|------------------------------------|----------------------------------------------------------------------------------|
| `bootstrap`                        | Jenkins jobs which are made when the server is first booted up.                  |
| `code-samples`                     | Jenkins jobs for smaller code samples in my repositories, some used in articles. |
| `folders`                          | Folders on the Jenkins server used to group jobs.                                |
| `global-aws`                       | Jenkins jobs used with the global AWS infrastructure.                            |
| `infrastructure`                   | Generic jenkins jobs for infrastructure building.                                |
| `jarombek-com`                     | Jenkins jobs for the `jarombek.com` application.                                 |
| `manual`                           | Jenkins jobs that are manually created instead of through a seed job.            |
| `modules`                          | Groovy modules for creating reusable Job DSL Plugin scripts.                     |
| `saints-xctf`                      | Jenkins jobs for the SaintsXCTF application.                                     |

### Version History

**[v1.1.1](https://github.com/AJarombek/global-jenkins-jobs/tree/v1.1.1) - Remove Schedules**

> Release Date: Dec 13th, 2022

* Removing schedules from jobs that will transition to GitHub Actions.

**[v1.1.0](https://github.com/AJarombek/global-jenkins-jobs/tree/v1.1.0) - Functional Jenkins Jobs**

> Release Date: Sep 21st, 2022

* Removing unused/broken jobs, which may move to a different CI/CD tool like GitHub Actions.

### Resources

1) [Job DSL Plugin Docs](https://jenkinsci.github.io/job-dsl-plugin/#)
2) [GSuite Configuration with Jenkins for Emails](https://stackoverflow.com/a/27130058)
3) [Bash Pipe Status](https://unix.stackexchange.com/a/14276)
4) [Kubernetes Plugin](https://plugins.jenkins.io/kubernetes/)
