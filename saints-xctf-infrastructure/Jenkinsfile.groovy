/**
 * Jenkins script for testing the SaintsXCTF AWS infrastructure.
 * @author Andrew Jarombek
 * @since 3/24/2019
 */

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
                credentialsId: "865da7f9-6fc8-49f3-aa56-8febd149e72b",
                url: "git@github.com:AJarombek/saints-xctf-infrastructure.git"
            ]]
        ])
    }
    stage("Execute Unit Tests") {

        def env = get_env()

        dir("test") {
            try {
                ansiColor('xterm') {
                    def status = sh (
                        script: """
                            set +e
                            python3 --version
                            python3 -m venv env
                            source ./env/bin/activate
                            python3 -m pip install -r requirements.txt

                            export TEST_ENV=$env
                            python3 runner.py

                            exit_status=\$?
                            deactivate
                            exit \$exit_status
                        """,
                        returnStatus: true
                    )

                    if (status >= 1) {
                        currentBuild.result = "UNSTABLE"
                    }
                }
            } catch (Exception ex) {
                echo "Infrastructure Testing Failed"
                currentBuild.result = "UNSTABLE"
            }
        }
    }
    stage('Send Results via Email') {
        def statusColors = [
            SUCCESS: '#28a745',
            UNSTABLE: '#ffc107',
            FAILURE: '#dc3545',
            OTHER: '#bbb'
        ]

        def subject = "$env.JOB_NAME Build #$env.BUILD_NUMBER - $currentBuild.result"
        def body = """
            <body>
                <h1 style="font-family: Calibri, Arial, sans-serif">SaintsXCTF Infrastructure ${get_env()} Tests</h1>
                <p style="font-family: Calibri, Arial, sans-serif">
                    Build 
                    <a href="$env.BUILD_URL" style="font-weight: bold; color: #777;">$env.BUILD_NUMBER</a> 
                    Result:
                    <strong style="color: ${statusColors[currentBuild.result] ?: statusColors['OTHER']}">
                        $currentBuild.result
                    </strong>
                </p>
            </body>
        """

        emailext(
            subject: subject,
            body: body,
            to: "andrew@jarombek.com",
            mimeType: 'text/html'
        )
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
    def matches = JOB_NAME =~ /saints-xctf-infrastructure-(\w+)/
    return matches[0][1]
}