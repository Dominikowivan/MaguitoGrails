package maguitograils

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class MonsterMarketSpec extends Specification implements DomainUnitTest<MonsterMarket> {

    Minion charanguito
    Minion guitarrita
    Minion bajito
    MonsterMarket market
    List<MonsterCostAssociation> monsters

    def setup() {
        charanguito = new Minion(name:"Charangito", actualLife: 300, gold:100, maxLife: 300, coordinate: new Coordinate(x:0,y:0))
        guitarrita = new Minion(name:"Guitarrita", actualLife: 500, gold:200, maxLife: 500, coordinate: new Coordinate(x:0,y:0))
        bajito = new Minion(name:"Bajito", actualLife: 600, gold:300, maxLife: 700, coordinate: new Coordinate(x:0,y:0))

        def charanguitoAssociation = new MonsterCostAssociation(monster: charanguito, cost: 100)
        def guitarritaAssociation = new MonsterCostAssociation(monster: guitarrita, cost: 200)
        def bajitoAssociation = new MonsterCostAssociation(monster: bajito, cost : 300)

        monsters = [charanguitoAssociation, guitarritaAssociation, bajitoAssociation]

        market = new MonsterMarket(name: 'Market', monstersCatalogue: monsters)
    }

    def cleanup() {
    }

    void "A Monster Market has 3 monsters in its catalogue"() {
        expect: market.monstersCatalogue.size() == 3
    }

    void "A Monster Market has a monster named Guitarrita with 500 life that costs 200 gold coins in its catalogue"() {
        expect: market.monstersCatalogue.find{m -> m.monster == guitarrita}.cost == 200
    }
}
