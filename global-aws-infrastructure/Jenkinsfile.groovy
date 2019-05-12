/**
 * Jenkins script for testing the Global AWS infrastructure.
 * @author Andrew Jarombek
 * @since 3/24/2019
 */

node("master") {
    stage("checkout") {
        cleanWs()
        checkout([$class: 'GitSCM',
                  branches: [[name: '*/master']],
                  credentialsId: "865da7f9-6fc8-49f3-aa56-8febd149e72b",
                  userRemoteConfigs: [[url: "git@github.com:AJarombek/global-aws-infrastructure.git"]]])
    }
    stage("run-tests") {
        dir("test") {
            try {
                ansiColor('xterm') {
                    sh """
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