/**
 * Jenkins script for testing the SaintsXCTF API unit tests.
 * @author Andrew Jarombek
 * @since 3/27/2020
 */

@Library(['global-jenkins-library@master']) _

def setupProject = {
    sh '''
        set +e
        set -x
        pipenv install
    '''
}

def executeTests = {
    try {
        def status = sh (
            script: """
                set +e
                set -x
                pipenv shell
                                
                exit_status=\$?
    
                cat test_results.log
                exit \$exit_status
            """,
            returnStatus: true
        )

        if (status >= 1) {
            currentBuild.result = "UNSTABLE"
        }

    } catch (Exception ex) {
        echo "SaintsXCTF API Testing Failed"
        currentBuild.result = "UNSTABLE"
    }
}

def emailTestResults = {
    def bodyContent = ""
    def testResultLog = readFile "test_results.log"

    testResultLog.split('\n').each {
        bodyContent += "<p style=\"font-family: Consolas,monaco,monospace\">$it</p>"
    }

    def bodyTitle = "SaintsXCTF API Tests"
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
        repository: 'jarombek-react-components',
        branch: env.branch,
        setupProjectScript: setupProject,
        executeTestsScript: executeTests
    ],
    post: [
        script: emailTestResults
    ]
]

pipelinejob(config)