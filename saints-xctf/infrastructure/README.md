### Overview

Jenkins jobs for the SaintsXCTF AWS infrastructure.

### Directories

| Directory Name                     | Description                                                                      |
|------------------------------------|----------------------------------------------------------------------------------|
| `create-acm`                       | Jenkins jobs to create ACM certificates for SaintsXCTF.                          |
| `create-bastion`                   | Jenkins jobs to create a bastion host in the `saints-xctf` VPC.                  |
| `create-database`                  | Jenkins jobs to create an RDS MySQL database for SaintsXCTF.                     |
| `create-database-snapshot`         | Jenkins jobs to create backup/restore functions for an RDS MySQL database.       |
| `create-saints-xctf-com-auth`      | Jenkins jobs to create the `auth.saintsxctf.com` AWS infrastructure.             |
| `create-secrets-manager`           | Jenkins jobs to create secrets in SecretsManager for SaintsXCTF.                 |
| `destroy-acm`                      | Jenkins jobs to destroy ACM certificates for SaintsXCTF.                         |
| `destroy-bastion`                  | Jenkins jobs to destroy a bastion host in the `saints-xctf` VPC.                 |
| `destroy-database`                 | Jenkins jobs to destroy an RDS MySQL database for SaintsXCTF.                    |
| `destroy-database-snapshot`        | Jenkins jobs to destroy backup/restore functions for an RDS MySQL database.      |
| `destroy-saints-xctf-com-auth`     | Jenkins jobs to destroy the `auth.saintsxctf.com` AWS infrastructure.            |
| `destroy-secrets-manager`          | Jenkins jobs to destroy secrets in SecretsManager for SaintsXCTF.                |
| `saints-xctf-infrastructure-test`  | Jenkins jobs to run tests on the SaintsXCTF AWS infrastructure in dev and prod.  |