/**
 * Seed job for CI trigger jobs located in application repositories.
 * @author Andrew Jarombek
 * @since 5/12/2019
 */

job('trigger-seed-job') {
    parameters {
        stringParam('repository', '', 'Repository containing a Job DSL Script for a trigger job')
        stringParam('branch', '', 'Branch in the repository to checkout')
        stringParam('path', '', 'Path in the repository where the Job DSL Script is located')
    }
    scm {
        git {
            branch("\$branch")
            remote {
                credentials("865da7f9-6fc8-49f3-aa56-8febd149e72b")
                github("AJarombek/\$repository", "ssh", "github.com")
            }
        }
    }
    steps {
        dsl {
            external("\$path")
        }
    }
}