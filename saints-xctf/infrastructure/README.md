### Overview

Jenkins jobs for the SaintsXCTF AWS infrastructure.

### Directories

| Directory Name                     | Description                                                                     |
|------------------------------------|---------------------------------------------------------------------------------|
| `backup-database`                  | Jenkins job to backup a database, placing the `.sql` file in S3.                |
| `create-acm`                       | Jenkins job to create ACM certificates for SaintsXCTF.                          |
| `create-bastion`                   | Jenkins job to create a bastion host in the `saints-xctf` VPC.                  |
| `create-database`                  | Jenkins job to create an RDS MySQL database for SaintsXCTF.                     |
| `create-database-backup`           | Jenkins job to create an S3 bucket that holds RDS MySQL database backups.       |
| `create-database-snapshot`         | Jenkins job to create backup/restore functions for an RDS MySQL database.       |
| `create-saints-xctf-com`           | Jenkins job to create the `saintsxctf.com` AWS infrastructure.                  |
| `create-saints-xctf-com-api`       | Jenkins job to create the `api.saintsxctf.com` AWS infrastructure.              |
| `create-saints-xctf-com-auth`      | Jenkins job to create the `auth.saintsxctf.com` AWS infrastructure.             |
| `create-saints-xctf-com-uasset`    | Jenkins job to create the `uasset.saintsxctf.com` AWS infrastructure.           |
| `create-secrets-manager`           | Jenkins job to create secrets in SecretsManager for SaintsXCTF.                 |
| `destroy-acm`                      | Jenkins job to destroy ACM certificates for SaintsXCTF.                         |
| `destroy-bastion`                  | Jenkins job to destroy a bastion host in the `saints-xctf` VPC.                 |
| `destroy-database`                 | Jenkins job to destroy an RDS MySQL database for SaintsXCTF.                    |
| `destroy-database-backup`          | Jenkins job to destroy an S3 bucket that holds RDS MySQL database backups.      |
| `destroy-database-snapshot`        | Jenkins job to destroy backup/restore functions for an RDS MySQL database.      |
| `destroy-saints-xctf-com`          | Jenkins job to destroy the `saintsxctf.com` AWS infrastructure.                 |
| `destroy-saints-xctf-com-api`      | Jenkins job to destroy the `api.saintsxctf.com` AWS infrastructure.             |
| `destroy-saints-xctf-com-auth`     | Jenkins job to destroy the `auth.saintsxctf.com` AWS infrastructure.            |
| `destroy-saints-xctf-com-uasset`   | Jenkins job to destroy the `uasset.saintsxctf.com` AWS infrastructure.          |
| `destroy-secrets-manager`          | Jenkins job to destroy secrets in SecretsManager for SaintsXCTF.                |
| `restore-database`                 | Jenkins job to restore a database from a backup.                                |
| `saints-xctf-infrastructure-test`  | Jenkins jobs to run tests on the SaintsXCTF AWS infrastructure in dev and prod. |