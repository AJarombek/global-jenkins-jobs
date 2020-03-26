/**
 * Create all the folders on the Jenkins server.  This creates namespaces for jobs based on the repository they are
 * used for (or if they are globally used).
 * @author Andrew Jarombek
 * @since 5/14/2019
 */

/**
 * Reusable function which returns the columns displayed within a folders list view.
 * @return Jenkins Job DSL Plugin closure with all the columns in a list view.
 */
def listViewColumns() {
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
            listViewColumns()
        }
        listView('Packer') {
            description('Packer jobs used globally for building AMIs for AWS')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names('bake-ami')
            }
            listViewColumns()
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
            listViewColumns()
        }
        listView('Continuous Integration') {
            description('Continuous Integration (CI) jobs for the Kubernetes Prototype')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names('kubernetes-prototype-ci')
            }
            listViewColumns()
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
            listViewColumns()
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
            listViewColumns()
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
            listViewColumns()
        }
    }
}

// Repository Specific: jarombek-react-components
folder('jarombek-react-components') {
    displayName('jarombek-react-components')
    description('Folder for the jarombek-react-components Repository')
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
            description('Continuous Integration jobs for the Reusable React Component Library')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names('jarombek-react-components-test')
            }
            listViewColumns()
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
            listViewColumns()
        }
        listView('Continuous Deployment') {
            description('Continuous Deployment jobs for the SaintsXCTF Infrastructure')
            filterBuildQueue()
            filterExecutors()
            jobs {
                names('saints-xctf-rds-snapshot-lambda')
            }
            listViewColumns()
        }
    }
}

// Repository Specific: saints-xctf-web
folder('saints-xctf-web') {
    displayName('saints-xctf-web')
    description('Folder for the saints-xctf-web Repository')
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
            listViewColumns()
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
            listViewColumns()
        }
    }
}