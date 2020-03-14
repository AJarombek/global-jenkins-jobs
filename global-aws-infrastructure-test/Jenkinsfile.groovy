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
                  credentialsId: "ajarombek-github",
                  userRemoteConfigs: [[url: "git@github.com:AJarombek/global-aws-infrastructure.git"]]])
    }
    stage("run-tests") {
        dir("test/src") {
            try {
                ansiColor('xterm') {
                    sh """
                        python3 --version
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