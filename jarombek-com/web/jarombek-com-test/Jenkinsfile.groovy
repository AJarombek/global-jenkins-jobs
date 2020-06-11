/**
 * Jenkins script for testing jarombek-com.
 * @author Andrew Jarombek
 * @since 5/12/2019
 */

@Library(['global-jenkins-library@master']) _

def setupProject = {
    sh '''
        set +x
        node --version
        npm --version
        yarn --version
        
        yarn
    '''
}

def executeTests = {
    try {
        def status = sh (
            script: "#!/bin/bash \n" +
            """
                set +e
                set -x
                yarn client:test 2>&1 | tee test_results.log
                exit_status_client=\${PIPESTATUS[0]}

                yarn server:test 2>&1 | tee -a test_results.log
                exit_status_server=\${PIPESTATUS[0]}
                
                exit_status=\$((exit_status_client + exit_status_server))
    
                exit \$exit_status
            """,
            returnStatus: true
        )

        if (status >= 1) {
            currentBuild.result = "UNSTABLE"
        }

    } catch (Exception ex) {
        echo "Jarombek Com Testing Failed"
        currentBuild.result = "UNSTABLE"
    }
}

def emailTestResults = {
    def bodyContent = ""
    def testResultLog = readFile "test_results.log"

    testResultLog.split('\n').each {
        bodyContent += "<p style=\"font-family: Consolas,monaco,monospace\">$it</p>"
    }

    def bodyTitle = "Jarombek Com Application Tests"
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
        repository: 'jarombek-com',
        branch: env.branch,
        setupProjectScript: setupProject,
        executeTestsScript: executeTests
    ],
    post: [
        script: emailTestResults
    ]
]

pipelinejob(config)
