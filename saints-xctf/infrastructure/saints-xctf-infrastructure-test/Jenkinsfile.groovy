/**
 * Jenkins script for testing the SaintsXCTF AWS infrastructure.
 * @author Andrew Jarombek
 * @since 3/24/2019
 */

@Library(['global-jenkins-library@master']) _

final String PARAM_BRANCH = env.branch

node("master") {
    stage("Checkout Test Suite") {
        cleanWs()
        checkout([
            $class: 'GitSCM',
            branches: [[name: "*/$PARAM_BRANCH"]],
            extensions: [
                [$class: 'SparseCheckoutPaths', SparseCheckoutPaths: [[path: 'test']]]
            ],
            userRemoteConfigs: [[
                credentialsId: "ajarombek-github",
                url: "git@github.com:AJarombek/saints-xctf-infrastructure.git"
            ]]
        ])
    }
    stage("Execute Tests") {

        def env = get_env()

        dir("test") {
            try {
                ansiColor('xterm') {
                    def status = sh (
                        script: "#!/bin/bash \n" +
                        """
                            set +e
                            set -x
                            python3.8 --version
                            python3.8 -m pip --version

                            python3.8 -m venv env
                            source ./env/bin/activate
                            python3.8 -m pip install -r requirements.txt

                            # The AWS SDK needs to know which region the infrastructure is in.
                            export AWS_DEFAULT_REGION=us-east-1
                            
                            export TEST_ENV=$env
                            python3.8 runner.py test_results.log
                            exit_status=\$?

                            cat test_results.log

                            deactivate
                            exit \$exit_status
                        """,
                        returnStatus: true
                    )

                    if (status >= 1) {
                        currentBuild.result = "UNSTABLE"
                    } else {
                        currentBuild.result = "SUCCESS"
                    }
                }
            } catch (Exception ex) {
                echo "Infrastructure Testing Failed"
                currentBuild.result = "FAILURE"
            }
        }
    }
    stage('Send Results via Email') {
        def bodyContent = ""
        def testResultLog = ""

        dir("test") {
            testResultLog = readFile "test_results.log"
        }

        testResultLog.split('\n').each {
            bodyContent += "<p style=\"font-family: Consolas,monaco,monospace\">$it</p>"
        }

        def bodyTitle = "SaintsXCTF Infrastructure ${get_env().toUpperCase()} Tests"
        email.sendEmail(bodyTitle, bodyContent, env.JOB_NAME, currentBuild.result, env.BUILD_NUMBER, env.BUILD_URL)
    }
    stage('Clean Workspace') {
        cleanWs()
    }
}

/**
 * Get the environment to test from the job name
 * @return The environment represented as a string
 */
@NonCPS
def get_env() {
    def matches = JOB_NAME =~ /saints-xctf-infrastructure-test-(\w+)/
    return matches[0][1]
}