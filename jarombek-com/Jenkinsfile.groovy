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
                  credentialsId : "865da7f9-6fc8-49f3-aa56-8febd149e72b",
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
