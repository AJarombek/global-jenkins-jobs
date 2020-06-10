#!/usr/bin/groovy

/**
 * Jenkins script for applying destroying the EKS infrastructure if it is still running first thing in the morning.
 * @author Andrew Jarombek
 * @since 5/23/2019
 */

node("master") {
    stage("Trigger") {
        build job: 'kubernetes-prototype-infra', parameters: [
            [$class: 'ChoiceParameterValue', name: 'Operation', value: 'destroy']
        ]
    }
}