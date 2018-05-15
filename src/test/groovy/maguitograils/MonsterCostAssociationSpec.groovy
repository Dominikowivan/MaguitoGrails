package maguitograils

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class MonsterCostAssociationSpec extends Specification implements DomainUnitTest<MonsterCostAssociation> {

    Monster bajito
    MonsterCostAssociation association

    def setup() {
        bajito = new Minion(name:"Bajito", actualLife: 600, gold:300, maxLife: 700, coordinate: new Coordinate(x:0,y:0))
        association = new MonsterCostAssociation(monster: bajito, cost : 300)
    }

    def cleanup() {
    }

    void "An association has a monster and a cost"() {
        expect:
            association.monster == bajito
            association.cost == 300
    }
}
