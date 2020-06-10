/**
 * Jenkins script for testing the react-16-3-demo application.
 * @author Andrew Jarombek
 * @since 4/18/2020
 */

@Library(['global-jenkins-library@master']) _

def setupProject = {
    sh '''
        set -x
        nodejs --version
        npm --version
        yarn --version
        
        yarn
    '''
}

def executeTests = {
    try {
        dir('app') {
            def status = sh (
                script: "#!/bin/bash \n" +
                """
                    set +e
                    set -x
                    yarn test 2>&1 | tee test_results.log
                    exit_status=\${PIPESTATUS[0]}
        
                    exit \$exit_status
                """,
                returnStatus: true
            )

            if (status >= 1) {
                currentBuild.result = "UNSTABLE"
            }
        }

    } catch (Exception ex) {
        echo "React 16.3 Demo Testing Failed"
        currentBuild.result = "UNSTABLE"
    }
}

def emailTestResults = {
    def bodyContent = ""
    def testResultLog = readFile "test_results.log"

    testResultLog.split('\n').each {
        bodyContent += "<p style=\"font-family: Consolas,monaco,monospace\">$it</p>"
    }

    def bodyTitle = "React 16.3 Demo Application Tests"
    email.sendEmail(
        bodyTitle,
        bodyContent,
        env.JOB_NAME,
        currentBuild.result,
        env.BUILD_NUMBER,
        env.BUILD_URL
    )

    cleanWs()
}

def config = [
    agent: [
        label: 'master'
    ],
    triggers: [
        cron: 'H 0 * * *'
    ],
    options: [
        time: 1,
        unit: 'HOURS',
        daysToKeepStr: '10',
        numToKeepStr: '5'
    ],
    stages: [
        repository: 'react-16-3-demo',
        branch: env.branch,
        setupProjectScript: setupProject,
        executeTestsScript: executeTests
    ],
    post: [
        script: emailTestResults
    ]
]

pipelinejob(config)
