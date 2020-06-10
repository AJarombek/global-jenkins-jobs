/**
 * Jenkins script for testing kubernetes-prototype.
 * @author Andrew Jarombek
 * @since 5/12/2019
 */

node("master") {
    stage("checkout") {
        cleanWs()
        checkout([$class : 'GitSCM',
                  branches : [[name: '*/master']],
                  credentialsId : "ajarombek-github",
                  userRemoteConfigs: [[url: "git@github.com:AJarombek/kubernetes-prototype.git"]]])
    }
    stage("infra tests") {
        dir("infra/test") {
            ansiColor('xterm') {
                sh "python3 masterTestSuite.py"
            }
        }
    }
    stage("server tests") {
        dir("app/client/plates") {
            ansiColor('xterm') {
                sh "npm run test"
            }
        }
    }
    stage("client tests") {
        dir("app/server") {
            ansiColor('xterm') {
                sh "npm run test"
            }
        }
    }
}
