/**
 * Jenkins script for initializing the Jenkins server.  Run this pipeline when the server first boots up.
 * @author Andrew Jarombek
 * @since 6/20/2020
 */

@Library(['global-jenkins-library@master']) _

pipeline {
    agent {
        label 'master'
    }
    options {
        ansiColor('xterm')
        timeout(time: 1, unit: 'HOURS')
        buildDiscarder(
            logRotator(daysToKeepStr: '10', numToKeepStr: '5')
        )
    }
    stages {
        stage("Clean Workspace") {
            steps {
                script {
                    cleanWs()
                }
            }
        }
        stage("Init Scripts") {
            steps {
                script {
                    sh """
                        # Install additional dependencies not handled by the Dockerfile
                        
                        # 1) Bazel
                        curl https://bazel.build/bazel-release.pub.gpg | sudo apt-key add -
                        echo "deb [arch=amd64] https://storage.googleapis.com/bazel-apt stable jdk1.8" | sudo tee /etc/apt/sources.list.d/bazel.list
                        sudo apt-get update
                        sudo apt-get install bazel
                        
                        # 2) aws-iam-authenticator
                        sudo curl -o aws-iam-authenticator https://amazon-eks.s3.us-west-2.amazonaws.com/1.16.8/2020-04-16/bin/linux/amd64/aws-iam-authenticator
                        sudo chmod +x ./aws-iam-authenticator
                        sudo cp ./aws-iam-authenticator /usr/local/bin/aws-iam-authenticator
                        
                        # 3) Terraform (via tfenv)
                        sudo rm -r ~/.tfenv
                        sudo rm /usr/local/bin/terraform
                        sudo rm /usr/local/bin/tfenv
                        sudo git clone https://github.com/tfutils/tfenv.git ~/.tfenv
                        sudo ln -s ~/.tfenv/bin/* /usr/local/bin
                        tfenv --version
                        sudo tfenv install latest
                        sudo tfenv use latest
                        
                        # 4) Zip
                        sudo apt-get install zip
                        zip --version
                        
                        # Installed Libraries
                        docker --version
                        python --version
                        node --version
                        aws --version
                        terraform --version
                        bazel --version
                        aws-iam-authenticator version
                    """
                }
            }
        }
        stage("Create Jobs") {
            steps {
                script {
                    // The first time JOB DSL scripts are built, they will fail and need approval.
                    build(job: 'set-folders', propagate: false, wait: true)

                    build(
                        job: 'seed-job',
                        parameters: [
                            string(name: 'repository', value: 'global-jenkins-jobs'),
                            string(name: 'branch', value: 'master')
                        ],
                        propagate: false
                    )

                    // Pause the job until the user approves the scripts.
                    timeout(time: 1, unit: 'HOURS') {
                        input message: 'Approve Scripts before continuing...', ok: 'Scripts Approved'
                    }

                    // On the Job DSL scripts second run, they should pass.
                    build(job: 'set-folders', propagate: true, wait: true)

                    build(
                        job: 'seed-job',
                        parameters: [
                            string(name: 'repository', value: 'global-jenkins-jobs'),
                            string(name: 'branch', value: 'master')
                        ]
                    )
                }
            }
        }
        stage("Trigger Initial Scheduled Jobs") {
            steps {
                script {
                    buildJobs([
                        'global-aws/cost-detection',
                        'global-aws/global-aws-infrastructure-test-prod',
                        'global-aws/global-aws-infrastructure-test-dev',
                        'global-aws/global-kubernetes-infrastructure-test',
                        'jarombek-com/components/jarombek-react-components-test',
                        'jarombek-com/infrastructure/jarombek-com-infrastructure-test-prod',
                        'jarombek-com/infrastructure/jarombek-com-infrastructure-test-dev',
                        'jarombek-com/web/jarombek-com-test',
                        'prototypes/graphql-react-prototype/graphql-react-prototype-test',
                        'prototypes/react-16-3-demo-test',
                        'saints-xctf/api/saints-xctf-api-test',
                        'saints-xctf/auth/saints-xctf-auth-test',
                        'saints-xctf/infrastructure/saints-xctf-infrastructure-test-prod',
                        'saints-xctf/infrastructure/saints-xctf-infrastructure-test-dev',
                        'saints-xctf/infrastructure/saints-xctf-kubernetes-test-prod',
                        'saints-xctf/infrastructure/saints-xctf-kubernetes-test-dev',
                        'saints-xctf/web/saints-xctf-web-test'
                    ])
                }
            }
        }
    }
    post {
        always {
            script {
                email.sendEmail(
                    "Jenkins Server Initialized",
                    "",
                    env.JOB_NAME,
                    currentBuild.result,
                    env.BUILD_NUMBER,
                    env.BUILD_URL
                )

                cleanWs()
            }
        }
    }
}

def buildJobs(List<String> jobList) {
    jobList.each {
        build(job: it, propagate: false, wait: false)
    }
}