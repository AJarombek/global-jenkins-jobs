/**
 * Create all the folders on the Jenkins server.  This creates namespaces for jobs based on the repository they are
 * used for (or if they are globally used).
 * @author Andrew Jarombek
 * @since 5/14/2019
 */

// Code Sample Jobs
folder('code-samples') {
    displayName('code-samples')
    description('Folder for small code sample jobs.')
}

// Python Code Sample Jobs
folder('code-samples/python') {
    displayName('python')
    description('Folder for small Python code sample jobs.')
    primaryView('All')
    authorization {
        permissions('andy', [
            'hudson.model.Item.Create',
            'hudson.model.Item.Discover'
        ])
        permission('hudson.model.Item.Discover', 'guest')
    }
    views {
        listView('Continuous Integration') {
            description('Continuous Integration jobs for small Python code samples')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names('test-concurrency')
            }
            columns() {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
    }
}

// Global AWS Infrastructure Jobs
folder('global-aws') {
    displayName('global-aws')
    description('Folder for global AWS infrastructure jobs.')
    primaryView('All')
    authorization {
        permissions('andy', [
            'hudson.model.Item.Create',
            'hudson.model.Item.Discover'
        ])
        permission('hudson.model.Item.Discover', 'guest')
    }
    views {
        listView('Continuous Integration') {
            description('Continuous Integration jobs for the Global AWS Infrastructure')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names(
                    'cost-detection',
                    'global-aws-infrastructure-test-dev',
                    'global-aws-infrastructure-test-prod',
                    'global-kubernetes-infrastructure-test'
                )
            }
            columns() {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
        listView('Continuous Deployment') {
            description('Continuous Deployment jobs for the Global AWS Infrastructure')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names(
                    'create-acm',
                    'create-budgets',
                    'create-cloud-trail',
                    'create-lambda-layer',
                    'create-route53',
                    'destroy-acm',
                    'destroy-budgets',
                    'destroy-cloud-trail',
                    'destroy-lambda-layer',
                    'destroy-route53',
                    'dockerfiles-push-images',
                    'push-jenkins-ecr-docker-image'
                )
            }
            columns() {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
    }
}

// Infrastructure Jobs
folder('infrastructure') {
    displayName('infrastructure')
    description('Folder for infrastructure automation jobs.')
    primaryView('All')
    authorization {
        permissions('andy', [
            'hudson.model.Item.Create',
            'hudson.model.Item.Discover'
        ])
        permission('hudson.model.Item.Discover', 'guest')
    }
    views {
        listView('Terraform') {
            description('Terraform jobs used globally for IaC')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names('terraform-apply', 'terraform-destroy')
            }
            columns() {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
        listView('Packer') {
            description('Packer jobs used globally for building AMIs for AWS')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names('bake-ami')
            }
            columns() {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
    }
}

// jarombek.com Application Jobs
folder('jarombek-com') {
    displayName('jarombek-com')
    description('Folder for the jarombek.com application')
}

// Jarombek Reusable Component Library Jobs
folder('jarombek-com/components') {
    displayName('components')
    description('Folder for the Jarombek reusable component library')
    primaryView('All')
    authorization {
        permissions('andy', [
            'hudson.model.Item.Create',
            'hudson.model.Item.Discover'
        ])
        permission('hudson.model.Item.Discover', 'guest')
    }
    views {
        listView('Continuous Integration') {
            description('Continuous Integration jobs for the Jarombek reusable component library')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names('jarombek-react-components-test')
            }
            columns() {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
    }
}

// jarombek.com AWS Infrastructure Jobs
folder('jarombek-com/infrastructure') {
    displayName('infrastructure')
    description('Folder for the jarombek.com AWS infrastructure')
    primaryView('All')
    authorization {
        permissions('andy', [
            'hudson.model.Item.Create',
            'hudson.model.Item.Discover'
        ])
        permission('hudson.model.Item.Discover', 'guest')
    }
    views {
        listView('Continuous Integration') {
            description('Continuous Integration jobs for the jarombek.com AWS infrastructure')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names('jarombek-com-aws-infrastructure-test', 'jarombek-com-kubernetes-infrastructure-test')
            }
            columns() {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
    }
}

// jarombek.com Web Application Jobs
folder('jarombek-com/web') {
    displayName('web')
    description('Folder for the jarombek.com web application')
    primaryView('All')
    authorization {
        permissions('andy', [
            'hudson.model.Item.Create',
            'hudson.model.Item.Discover'
        ])
        permission('hudson.model.Item.Discover', 'guest')
    }
    views {
        listView('Continuous Integration') {
            description('Continuous Integration jobs for the jarombek.com web application')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names('jarombek-com-test')
            }
            columns() {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
    }
}

// Prototype Application Jobs
folder('prototypes') {
    displayName('prototypes')
    description('Folder for prototype applications')
    primaryView('All')
    authorization {
        permissions('andy', [
            'hudson.model.Item.Create',
            'hudson.model.Item.Discover'
        ])
        permission('hudson.model.Item.Discover', 'guest')
    }
    views {
        listView('Continuous Integration') {
            description('Continuous Integration jobs for prototypes')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names(
                    'react-16-3-demo-test',
                    'react-webpack-seed',
                    'kubernetes-prototype-ci'
                )
            }
            columns() {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
        listView('Continuous Deployment') {
            description('Continuous Deployment jobs for prototypes')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names(
                    'cloudformation-serverless',
                    'kubernetes-prototype-infra',
                    'kubernetes-prototype-safeguard'
                )
            }
            columns() {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
    }
}

// Apollo Client & Server Prototype Jobs
folder('prototypes/apollo-client-server-prototype') {
    displayName('apollo-client-server-prototype')
    description('Folder for the Apollo Client & Server Prototype')
    primaryView('All')
    authorization {
        permissions('andy', [
            'hudson.model.Item.Create',
            'hudson.model.Item.Discover'
        ])
        permission('hudson.model.Item.Discover', 'guest')
    }
    views {
        listView('Continuous Integration') {
            description('Continuous Integration jobs for the Apollo Client & Server Prototype')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names('test-infrastructure', 'test-client', 'test-server')
            }
            columns() {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
        listView('Continuous Deployment') {
            description('Continuous Deployment jobs for the Apollo Client & Server Prototype')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names('push-images', 'create-infrastructure', 'destroy-infrastructure')
            }
            columns() {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
    }
}

// DevOps Prototype Jobs
folder('prototypes/devops-prototypes') {
    displayName('devops-prototypes')
    description('Folder for the DevOps Prototypes')
    primaryView('All')
    authorization {
        permissions('andy', [
            'hudson.model.Item.Create',
            'hudson.model.Item.Discover'
        ])
        permission('hudson.model.Item.Discover', 'guest')
    }
    views {
        listView('Continuous Integration') {
            description('Continuous Integration jobs for the DevOps Prototypes')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names('dynamodb-sample-test')
            }
            columns() {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
    }
}

// GraphQL React Prototype Jobs
folder('prototypes/graphql-react-prototype') {
    displayName('graphql-react-prototype')
    description('Folder for the GraphQL React Prototype')
    primaryView('All')
    authorization {
        permissions('andy', [
            'hudson.model.Item.Create',
            'hudson.model.Item.Discover'
        ])
        permission('hudson.model.Item.Discover', 'guest')
    }
    views {
        listView('Continuous Integration') {
            description('Continuous Integration jobs for the GraphQL React Prototype')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names('graphql-react-prototype-test')
            }
            columns() {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
        listView('Continuous Deployment') {
            description('Continuous Deployment jobs for the GraphQL React Prototype')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names(
                    'push-app-image',
                    'push-base-image',
                    'create-acm-infrastructure',
                    'create-ecr-infrastructure',
                    'create-k8s-infrastructure',
                    'destroy-acm-infrastructure',
                    'destroy-ecr-infrastructure',
                    'destroy-k8s-infrastructure'
                )
            }
            columns() {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
    }
}

// SaintsXCTF Application Jobs
folder('saints-xctf') {
    displayName('saints-xctf')
    description('Folder for the SaintsXCTF application')
}

// SaintsXCTF API Application Jobs
folder('saints-xctf/api') {
    displayName('api')
    description('Folder for the SaintsXCTF API application')
    primaryView('All')
    authorization {
        permissions('andy', [
            'hudson.model.Item.Create',
            'hudson.model.Item.Discover'
        ])
        permission('hudson.model.Item.Discover', 'guest')
    }
    views {
        listView('Continuous Integration') {
            description('Continuous Integration jobs for the SaintsXCTF API Application')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names('saints-xctf-api-test')
            }
            columns() {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
        listView('Continuous Deployment') {
            description('Continuous Deployment jobs for the SaintsXCTF API Application')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names('push-image')
            }
            columns() {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
    }
}

// SaintsXCTF Auth Application Jobs
folder('saints-xctf/auth') {
    displayName('auth')
    description('Folder for the SaintsXCTF Auth application')
    primaryView('All')
    authorization {
        permissions('andy', [
            'hudson.model.Item.Create',
            'hudson.model.Item.Discover'
        ])
        permission('hudson.model.Item.Discover', 'guest')
    }
    views {
        listView('Continuous Integration') {
            description('Continuous Integration jobs for the SaintsXCTF Auth Application')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names('saints-xctf-auth-test')
            }
            columns() {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
        listView('Continuous Deployment') {
            description('Continuous Deployment jobs for the SaintsXCTF Auth Application')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names(
                    'push-authenticate-lambda-image',
                    'push-authorizer-lambda-image',
                    'push-mock-auth-image',
                    'push-rotate-lambda-image',
                    'push-token-lambda-image'
                )
            }
            columns() {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
    }
}

// SaintsXCTF Function Application Jobs
folder('saints-xctf/function') {
    displayName('function')
    description('Folder for the SaintsXCTF Function application')
    primaryView('All')
    authorization {
        permissions('andy', [
            'hudson.model.Item.Create',
            'hudson.model.Item.Discover'
        ])
        permission('hudson.model.Item.Discover', 'guest')
    }
    views {
        listView('Continuous Deployment') {
            description('Continuous Deployment jobs for the SaintsXCTF Function Application')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names(
                    'push-mock-fn-image'
                )
            }
            columns() {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
    }
}

// SaintsXCTF Infrastructure Jobs
folder('saints-xctf/infrastructure') {
    displayName('infrastructure')
    description('Folder for the SaintsXCTF Infrastructure')
    primaryView('All')
    authorization {
        permissions('andy', [
            'hudson.model.Item.Create',
            'hudson.model.Item.Discover'
        ])
        permission('hudson.model.Item.Discover', 'guest')
    }
    views {
        listView('Continuous Integration') {
            description('Continuous Integration jobs for the SaintsXCTF Infrastructure')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names(
                    'saints-xctf-infrastructure-test-prod',
                    'saints-xctf-infrastructure-test-dev',
                    'saints-xctf-kubernetes-test-prod',
                    'saints-xctf-kubernetes-test-dev'
                )
            }
            columns() {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
        listView('Continuous Deployment') {
            description('Continuous Deployment jobs for the SaintsXCTF Infrastructure')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names(
                    'backup-database',
                    'create-acm',
                    'create-bastion',
                    'create-database',
                    'create-database-backup',
                    'create-database-client',
                    'create-database-deployment',
                    'create-database-snapshot',
                    'create-saints-xctf-com',
                    'create-saints-xctf-com-api',
                    'create-saints-xctf-com-asset',
                    'create-saints-xctf-com-auth',
                    'create-saints-xctf-com-fn',
                    'create-saints-xctf-com-ingress',
                    'create-saints-xctf-com-uasset',
                    'create-secrets-manager',
                    'database-script-deployment',
                    'destroy-acm',
                    'destroy-bastion',
                    'destroy-database',
                    'destroy-database-backup',
                    'destroy-database-client',
                    'destroy-database-deployment',
                    'destroy-database-snapshot',
                    'destroy-saints-xctf-com',
                    'destroy-saints-xctf-com-api',
                    'destroy-saints-xctf-com-asset',
                    'destroy-saints-xctf-com-auth',
                    'destroy-saints-xctf-com-fn',
                    'destroy-saints-xctf-com-ingress',
                    'destroy-saints-xctf-com-uasset',
                    'destroy-secrets-manager',
                    'restore-database',
                    'scheduling-dev-database'
                )
            }
            columns() {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
    }
}

// SaintsXCTF Web Application Jobs
folder('saints-xctf/web') {
    displayName('web')
    description('Folder for the SaintsXCTF web application')
    primaryView('All')
    authorization {
        permissions('andy', [
            'hudson.model.Item.Create',
            'hudson.model.Item.Discover'
        ])
        permission('hudson.model.Item.Discover', 'guest')
    }
    views {
        listView('Continuous Integration') {
            description('Continuous Integration jobs for the SaintsXCTF Web Application')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names('saints-xctf-web-e2e', 'saints-xctf-web-lint', 'saints-xctf-web-snapshot')
            }
            columns() {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
        listView('Continuous Deployment') {
            description('Continuous Deployment jobs for the SaintsXCTF Web Application')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names('push-image')
            }
            columns() {
                status()
                weather()
                name()
                lastSuccess()
                lastFailure()
                lastDuration()
                buildButton()
            }
        }
    }
}