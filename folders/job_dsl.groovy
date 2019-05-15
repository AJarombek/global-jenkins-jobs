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
        listView('All') {
            description('All jobs for the Kubernetes Prototype')
            filterBuildQueue()
            filterExecuters()
            jobs {
                names('terraform-apply', 'terraform-destroy', 'bake-ami')
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
        listView('All') {
            description('All jobs for the Kubernetes Prototype')
            filterBuildQueue()
            filterExecuters()
            jobs {
                names('kubernetes-prototype-ci', 'kubernetes-prototype-infra')
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