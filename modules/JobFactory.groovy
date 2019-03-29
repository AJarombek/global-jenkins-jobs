/**
 * Factory to build Jenkins Job DSL scripts
 * @author Andrew Jarombek
 * @since 3/26/2019
 */

class JobFactory {

    def dsl

    JobFactory(dsl) {
        this.dsl = dsl
    }
}