/**
 * Jenkins script for testing saints-xctf-web.
 * @author Andrew Jarombek
 * @since 3/14/2020
 */

// 15 West 64th Street, Begin setting up furniture, Too many IKEA trips

@Library(['global-jenkins-library@master']) _

def setupProject = {
    sh '''
        set -x
        nodejs --version
        npm --version
        yarn --version
        
        git config --global url."https://".insteadOf git://
        yarn
    '''
}

def executeTests = {
    try {
        def status = sh (
            script: """
                set +e
                set -x
                yarn client:test 2>&1 | tee test_results.log
                exit_status=\$?

                exit \$exit_status
            """,
            returnStatus: true
        )

        if (status >= 1) {
            currentBuild.result = "UNSTABLE"
        }

    } catch (Exception ex) {
        echo "SaintsXCTF Web Application Testing Failed"
        currentBuild.result = "UNSTABLE"
    }
}

def emailTestResults = {
    def bodyContent = ""
    def testResultLog = readFile "test_results.log"

    testResultLog.split('\n').each {
        bodyContent += "<p style=\"font-family: Consolas,monaco,monospace\">$it</p>"
    }

    def bodyTitle = "SaintsXCTF Web Application Tests"
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
        repository: 'saints-xctf-web',
        branch: env.branch,
        setupProjectScript: setupProject,
        executeTestsScript: executeTests
    ],
    post: [
        script: emailTestResults
    ]
]

pipelinejob(config)
