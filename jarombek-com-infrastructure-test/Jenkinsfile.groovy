/**
 * Jenkins script for testing jarombek-com-infrastructure.
 * @author Andrew Jarombek
 * @since 6/2/2019
 */

final String PARAM_BRANCH = branch

node("master") {
    stage("checkout") {
        cleanWs()
        checkout([$class: 'GitSCM',
                  branches: [[name: "*/$PARAM_BRANCH"]],
                  credentialsId: "865da7f9-6fc8-49f3-aa56-8febd149e72b",
                  userRemoteConfigs: [[url: "git@github.com:AJarombek/jarombek-com-infrastructure.git"]]])
    }
    stage("run-tests") {
        dir("test/src") {
            try {
                ansiColor('xterm') {
                    sh """
                        python3.7 --version
                        export AWS_DEFAULT_REGION=us-east-1
                        python3.7 runner.py
                    """
                }
            } catch (Exception ex) {
                echo "Infrastructure Testing Failed"
                currentBuild.result = "UNSTABLE"
            }
        }
    }
}