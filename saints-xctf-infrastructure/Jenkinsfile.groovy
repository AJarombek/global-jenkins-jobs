/**
 * Jenkins script for testing the SaintsXCTF AWS infrastructure.
 * @author Andrew Jarombek
 * @since 3/24/2019
 */

node("master") {
    stage("checkout-test-suite") {
        cleanWs()
        checkout([$class: 'GitSCM',
                  branches: [[name: '*/master']],
                  credentialsId: "865da7f9-6fc8-49f3-aa56-8febd149e72b",
                  userRemoteConfigs: [[url: "git@github.com:AJarombek/saints-xctf-infrastructure.git"]]])
    }
    stage("run-tests") {

        def env = get_env()

        dir("test") {
            try {
                ansiColor('xterm') {
                    sh """
                        export TEST_ENV="$env"
                        python3 masterTestSuite.py
                    """
                }
            } catch (Exception ex) {
                echo "Infrastructure Testing Failed"
                currentBuild.result = "UNSTABLE"
            }
        }
    }
}

/**
 * Get the environment to test from the job name
 * @return The environment represented as a string
 */
@NonCPS
def get_env() {
    def matches = JOB_NAME =~ /saints-xctf-infrastructure-(\w+)/
    return matches[0][1]
}