package maguitograils

import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest

class DungeonServiceSpec extends HibernateSpec implements ServiceUnitTest<DungeonService>{

    Minion charanguito
    Minion guitarrita
    Minion bajito
    List<Minion> dungeonMonsters
    List<MonsterCostAssociation> marketMonsters
    Dungeon dungeon
    Dungeon anotherDungeon
    MonsterMarket monsterMarket
    DungeonService dungeonService

    def setup() {
        charanguito = new Minion(name:"Charangito", actualLife: 300, gold:100, maxLife: 300, coordinate: new Coordinate(x:0,y:0))
        guitarrita = new Minion(name:"Guitarrita", actualLife: 500, gold:200, maxLife: 500, coordinate: new Coordinate(x:0,y:0))
        bajito = new Minion(name:"Bajito", actualLife: 600, gold:300, maxLife: 700, coordinate: new Coordinate(x:0,y:0))

        def charanguitoAssociation = new MonsterCostAssociation(monster: charanguito, cost: 100)
        def guitarritaAssociation = new MonsterCostAssociation(monster: guitarrita, cost: 100)
        def bajitoAssociation = new MonsterCostAssociation(monster: bajito, cost : 300)

        dungeonMonsters = [charanguito]
        marketMonsters = [charanguitoAssociation, guitarritaAssociation, bajitoAssociation]

        dungeon = new Dungeon(name: "Dungy", monsters: dungeonMonsters, coordinate:new Coordinate(x:50,y:100),gold: 0)
        anotherDungeon = new Dungeon(name: "Dungex", coordinate:new Coordinate(x:200,y:320),gold: 100)

        monsterMarket = new MonsterMarket(name: 'Market', monstersCatalogue: marketMonsters)

        dungeonService = new DungeonService()

        dungeon.save()
        anotherDungeon.save()
        monsterMarket.save()
    }

    def cleanup() {
    }

    void "Una Dungeon compra un monstruo Charanguito a un comercio de monstruos"() {
        when: dungeonService.buyMonster(monsterMarket, anotherDungeon, guitarrita)
        then:
            Dungeon sameDungeon = Dungeon.findByName("Dungex")
            anotherDungeon.getName()        ==   sameDungeon.getName()
            anotherDungeon.getMonsters()    ==   sameDungeon.getMonsters()
            anotherDungeon.monsters.size()  ==   sameDungeon.monsters.size()
            anotherDungeon.getGold()        ==   sameDungeon.getGold()
            anotherDungeon.getCoordinate()  ==   sameDungeon.getCoordinate()
    }

    void "Una Dungeon sin dinero, no puede comprar monstruos"() {
        when: dungeonService.buyMonster(monsterMarket, dungeon, bajito)
        then:
            def persistedDungeon = Dungeon.findByName("Dungy")
            !persistedDungeon.monsters.contains(bajito)
    }

    void "Una Dungeon le compra un mounstruo a otra Dungeon"() {
        when: dungeonService.buyMonsterOwnedByDungeon(anotherDungeon,dungeon,charanguito,100)
        then:
            def buyerDungeon = Dungeon.findByName("Dungex")
            def sellerDungeon = Dungeon.findByName("Dungy")

            buyerDungeon.monsters.contains(charanguito)
            !sellerDungeon.monsters.contains(charanguito)
    }

    //TODO: agregar el test para probar que no se cumplieron las condiciones de compra
}
