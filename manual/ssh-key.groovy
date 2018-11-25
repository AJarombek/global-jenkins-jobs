/**
 * Jenkins pipeline job for generating an SSH key
 * @author Andrew Jarombek
 * @since 11/25/2018
 */

node("master") {
    // Check which directory we are currently in
    sh 'pwd'

    // Remove the existing public/private keys
    sh """
        rm ../../.ssh/id_rsa.pub
        rm ../../.ssh/id_rsa
    """

    // Generate a new public/private key for connecting to GitHub via SSH
    sh 'ssh-keygen -t rsa -N "" -f ../../.ssh/id_rsa'

    // Print out the public key to the screen.  The public key is used by GitHub
    sh 'cat ../../.ssh/id_rsa.pub'

    // Make sure that the public/private keys exist
    sh "ls -la ../../.ssh/"
}