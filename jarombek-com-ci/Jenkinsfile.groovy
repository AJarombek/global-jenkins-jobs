/**
 * Jenkins script for testing jarombek-com.
 * @author Andrew Jarombek
 * @since 5/12/2019
 */

node("master") {
    stage("checkout") {
        cleanWs()
        checkout([$class : 'GitSCM',
                  branches : [[name: '*/master']],
                  credentialsId : "ajarombek-github",
                  userRemoteConfigs: [[url: "git@github.com:AJarombek/jarombek-com.git"]]])
    }
    stage("Init") {
        ansiColor('xterm') {
            sh "yarn"
        }
    }
    stage("application tests") {
        ansiColor('xterm') {
            sh "yarn run client:server:test"
        }
    }
}
