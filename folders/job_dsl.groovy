/**
 * Create all the folders on the Jenkins server.  This creates namespaces for jobs based on the repository they are
 * used for (or if they are globally used).
 * @author Andrew Jarombek
 * @since 5/14/2019
 */

folder('kubernetes-prototype') {
    displayName('Kubernetes Prototype')
    description('Folder for the kubernetes-prototype Repository')
    primaryView('All')
    authorization {
        permissions('andy', [
            'hudson.model.Item.Create',
            'hudson.model.Item.Discover'
        ])
        permission('hudson.model.Item.Discover', 'guest')
    }
}