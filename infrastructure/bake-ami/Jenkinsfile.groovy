#!/usr/bin/groovy

@Library(['global-jenkins-library@master']) _

/**
 * Jenkins script for baking AMIs for the Jenkins server EC2 instance
 * @author Andrew Jarombek
 * @since 1/24/2019
 */

final String PARAM_REPOSITORY = repository
final String PARAM_BRANCH = branch
final String PARAM_DIRECTORY = path
final String PARAM_FILENAME = filename

pipeline {
    agent master
    packer.packerBuild(PARAM_DIRECTORY, PARAM_FILENAME, PARAM_REPOSITORY, PARAM_BRANCH)
}