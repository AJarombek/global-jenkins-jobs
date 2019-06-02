/**
 * Create all the folders on the Jenkins server.  This creates namespaces for jobs based on the repository they are
 * used for (or if they are globally used).
 * @author Andrew Jarombek
 * @since 5/14/2019
 */

// Global: DevOps
folder('devops-jobs') {
    displayName('DevOps Jobs')
    description('Folder for random DevOps jobs used globally')
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

// Global: Seed Jobs
folder('seed-jobs') {
    displayName('Seed Jobs')
    description('Folder for Job DSL Plugin Seed Jobs')
    primaryView('All')
    authorization {
        permissions('andy', [
                'hudson.model.Item.Create',
                'hudson.model.Item.Discover'
        ])
        permission('hudson.model.Item.Discover', 'guest')
    }
    views {}
}

// Repository Specific: kubernetes-prototype
folder('kubernetes-prototype') {
    displayName('kubernetes-prototype')
    description('Folder for the kubernetes-prototype Repository')
    primaryView('All')
    authorization {
        permissions('andy', [
            'hudson.model.Item.Create',
            'hudson.model.Item.Discover'
        ])
        permission('hudson.model.Item.Discover', 'guest')
    }
    views {
        listView('Infrastructure') {
            description('Infrastructure jobs for the Kubernetes Prototype')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names('kubernetes-prototype-infra', 'kubernetes-prototype-safeguard')
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
        listView('Continuous Integration') {
            description('Continuous Integration (CI) jobs for the Kubernetes Prototype')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names('kubernetes-prototype-ci')
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

// Repository Specific: global-aws-infrastructure
folder('global-aws-infrastructure') {
    displayName('global-aws-infrastructure')
    description('Folder for the global-aws-infrastructure Repository')
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
                names('global-aws-infrastructure-test')
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

// Repository Specific: jarombek-com
folder('jarombek-com') {
    displayName('jarombek-com')
    description('Folder for the jarombek-com Repository')
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
            description('Continuous Integration jobs for the jarombek.com Application')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names('jarombek-com-ci')
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

// Repository Specific: jarombek-com-infrastructure
folder('jarombek-com-infrastructure') {
    displayName('jarombek-com-infrastructure')
    description('Folder for the jarombek-com-infrastructure Repository')
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
            description('Continuous Integration jobs for the jarombek.com AWS Infrastructure')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names('jarombek-com-infrastructure-test-dev', 'jarombek-com-infrastructure-test-prod')
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

// Repository Specific: saints-xctf-infrastructure
folder('saints-xctf-infrastructure') {
    displayName('saints-xctf-infrastructure')
    description('Folder for the saints-xctf-infrastructure Repository')
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
    }
}

// Repository Specific: react-webpack-seed
folder('react-webpack-seed') {
    displayName('react-webpack-seed')
    description('Folder for the react-webpack-seed Repository')
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
            description('Continuous Integration jobs for the React.js and Webpack Application')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names('react-webpack-seed')
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