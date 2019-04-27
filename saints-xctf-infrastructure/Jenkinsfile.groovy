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

        // Get the environment from the job name
        def matches = JOB_NAME =~ /saints-xctf-infrastructure-(\w+)/
        def env = matches[0][1]

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