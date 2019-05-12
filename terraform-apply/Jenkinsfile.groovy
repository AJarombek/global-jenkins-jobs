#!/usr/bin/groovy

@Library(['global-jenkins-library@master']) _

/**
 * Jenkins script for applying changes to AWS infrastructure using Terraform
 * @author Andrew Jarombek
 * @since 11/21/2018
 */

node("master") {
    terraform.terraformApply(terraform_directory, repository_url, true)
}