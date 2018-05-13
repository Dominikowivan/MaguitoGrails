package maguitograils

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class ItemSpec extends Specification implements DomainUnitTest<Item> {

    Item      unBaculo

    def setup() {
        unBaculo      = new Item     (nombre: "baculo"     , peso: 10)
    }

    def "Un item se crea con un nombre y un precio"() {
        expect:
            unBaculo.getNombre() == "baculo"
            unBaculo.getPeso()   ==  10
    }

}