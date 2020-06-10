#!/usr/bin/groovy

@Library(['global-jenkins-library@master']) _

/**
 * Jenkins script for applying changes to AWS infrastructure using Terraform
 * @author Andrew Jarombek
 * @since 11/21/2018
 */

final String PARAM_REPO_NAME = repository_name
final String PARAM_TERRAFORM_DIR = terraform_directory

node("master") {
    terraform.terraformApply(PARAM_TERRAFORM_DIR, "git@github.com:AJarombek/$PARAM_REPO_NAME.git", true)
}