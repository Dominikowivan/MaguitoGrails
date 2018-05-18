package maguitograils

import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest
import maguitograils.Exception.TheyArentInTheSameCoordinateException

class PersonajeServiceSpec extends HibernateSpec implements ServiceUnitTest<PersonajeService> {

    Personaje        player1
    Personaje        player2
    Minion           monster1
    Minion           monster2
    PersonajeService ps
    List<Minion>     monsters
    Dungeon          dungeon

    def setup(){
        player1  = new Personaje(name:"a", gold: 0, pesoMaximo: 20, xp:30, actualLife:200, baseDamage: 100, coordinate: new Coordinate(x:1, y:1))
        player2  = new Personaje(name:"b", pesoMaximo: 20, xp:30, actualLife:200, baseDamage: 100, coordinate: new Coordinate(x:2, y:2))
        ps       = new PersonajeService()
        monster1 = new Minion(name:"m1", actualLife: 300, gold:100, maxLife: 300, baseDamage: 10, coordinate: new Coordinate(x:0,y:0))
        monster2 = new Minion(name:"m2", actualLife: 300, gold:100, maxLife: 300, baseDamage: 10, coordinate: new Coordinate(x:0,y:0))
        monsters = [monster1, monster2]
        dungeon  = new Dungeon(name: "Dungeon", monsters: monsters, coordinate: new Coordinate(x:50,y:100), gold: 20)
    }

    def 'A player combats to another'() {
        given:
            ps.savePersonaje(player1)
            ps.savePersonaje(player2)

        when:
            def combatResult = ps.combat(player1, player2)

        then:
            def p1 = ps.loadByNombre(player1.name)
            def p2 = ps.loadByNombre(player2.name)
            p1.actualLife == 100
            p2.actualLife == 0
            combatResult.turns.size() == 3
            combatResult.winner == player1
    }

    def "Two players can't combat because they aren't in the same coordinate"() {
        given:
            player2.setCoordinate(new Coordinate(x:1, y:1))
            ps.savePersonaje(player1)
            ps.savePersonaje(player2)

        when:
            ps.combat(player1, player2)

        then:
            thrown(TheyArentInTheSameCoordinateException)
    }

    def 'A player changes his coordinate'() {
        given:
            ps.savePersonaje(player1)
            def newCoordinate = new Coordinate(x: 6, y: 6)

        expect:
            def aPlayer = ps.loadByNombre(player1.name)
            aPlayer.coordinate.isEquals(player1.coordinate)

        when:
            ps.move(player1, newCoordinate)

        then:
            def player = ps.loadByNombre(player1.name)
            player.coordinate.isEquals(newCoordinate)
    }

    def 'A player explores a Dungeon and wins'() {
        given:
            ps.savePersonaje(player1)
            dungeon.save()

        when:
            def expeditionResult = ps.exploreDungeon(player1, dungeon)

        then:
            def player   = ps.loadByNombre(player1.name)
            def aDungeon = Dungeon.findByName(dungeon.name)
            player.actualLife == 170
            player.gold       == 220
            expeditionResult.isTheWinner(player1)
            expeditionResult.combatResults.size() == 2
            aDungeon.gold == 0
            aDungeon.monsters.every{it.gold == 0}
    }

    def 'A player explores a Dungeon and loses'() {
        given:
            player1.actualLife = 1
            player1.gold = 22
            ps.savePersonaje(player1)
            dungeon.save()

        when:
            def expeditionResult = ps.exploreDungeon(player1, dungeon)

        then:
            def player   = ps.loadByNombre(player1.name)
            def aDungeon = Dungeon.findByName(dungeon.name)
            player.actualLife == 0
            player.gold       == 0
            !expeditionResult.isTheWinner(player1)
            expeditionResult.combatResults.size() == 2
            aDungeon.gold == 42
            aDungeon.monsters.every{it.gold != 0}
    }

}
