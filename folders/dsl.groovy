/**
 * Create all the folders on the Jenkins server.  This creates namespaces for jobs based on the repository they are
 * used for (or if they are globally used).
 * @author Andrew Jarombek
 * @since 5/14/2019
 */

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
                names('cost-detection')
                names('global-aws-infrastructure-test-dev')
                names('global-aws-infrastructure-test-prod')
                names('global-kubernetes-infrastructure-test')
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
                names('push-jenkins-ecr-docker-image')
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
                names('jarombek-com-infrastructure-test')
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
                    'create-ecr-infrastructure',
                    'create-eks-infrastructure',
                    'destroy-ecr-infrastructure',
                    'destroy-eks-infrastructure'
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
        listView('Continuous Deployment') {
            description('Continuous Deployment jobs for the SaintsXCTF Auth Application')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names(
                    'push-authenticate-lambda-image',
                    'push-authorizer-lambda-image',
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
                names('saints-xctf-infrastructure-dev', 'saints-xctf-infrastructure-prod')
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
                    'create-acm',
                    'create-database',
                    'create-saints-xctf-com-auth',
                    'destroy-acm',
                    'destroy-database',
                    'destroy-saints-xctf-com-auth'
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
                names('saints-xctf-web-test')
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