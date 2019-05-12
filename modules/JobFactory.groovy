/**
 * Factory to build Jenkins Job DSL scripts
 * Source: [https://blog.thesparktree.com/you-dont-know-jenkins-part-2]
 * @author Andrew Jarombek
 * @since 3/26/2019
 */

class JobFactory {

    def dsl

    JobFactory(dsl) {
        this.dsl = dsl
    }
}