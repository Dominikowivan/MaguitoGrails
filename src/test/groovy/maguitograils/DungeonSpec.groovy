package maguitograils

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class DungeonSpec extends Specification implements DomainUnitTest<Dungeon> {

    Dungeon dungeon
    List<Minion> monsters

    def setup() {
        def charanguito = new Minion(name:"Charangito", actualLife: 300, gold:100, maxLife: 300, coordinate: new Coordinate(x:0,y:0))
        monsters = [charanguito]
        dungeon = new Dungeon(name: "Dungy", monsters: monsters, coordinate:new Coordinate(x:50,y:100),gold: 0)
    }

    def cleanup() {
    }

    void "Un Dungeon se llama Dungy y tiene 1 mounstruo"() {
        expect:
            dungeon.name            == "Dungy"
            dungeon.countMonster()   == 1
    }

    void "Un Dungeon con dos mounstruos uno esta abatido"() {
        given:
            def charango = new Minion(name:"Charango", actualLife: 300, gold:100, maxLife: 300, coordinate: new Coordinate(x:0,y:0))

        when:
            charango.getDamage(200)
            dungeon.add(charango)

        then:
            dungeon.countMonster() == 2
            dungeon.monstersWeary() == 1
    }
    void "un dungeon sabe el oro total de todos los monstruos que se encuentran dentro de el"(){
        given:
            def charango = new Minion(name:"Charango", actualLife: 300, gold:500,
                    maxLife: 300, coordinate: new Coordinate(x:0,y:0))
        when:
            dungeon.add(charango)

        then:
            dungeon.getMonstersTotalGold() == 600

    }

    void "Un dungeon recauda la plata de todos los monstruos que estan dentro de este"() {
        given:
        def charango = new Minion(name:"Charango", actualLife: 300, gold:500,
                maxLife: 300, coordinate: new Coordinate(x:0,y:0))
        when:
        dungeon.add(charango)
        dungeon.recollectGold()
        then:
            dungeon.gold == 600
    }

    void "cuando un dungeon recauda la plata de los monstruos se mueven a su cordenada"() {
        given:
        def charango = new Minion(name:"Charango", actualLife: 300, gold:500,
                maxLife: 300, coordinate: new Coordinate(x:0,y:0))
        when:
        dungeon.add(charango)
        dungeon.recollectGold()
        then:
        dungeon.gold == 600
        dungeon.monsters.any{ (it.coordinate == dungeon.coordinate) }
    }






}
