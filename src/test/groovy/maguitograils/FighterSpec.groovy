package maguitograils

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class FighterSpec extends Specification implements DomainUnitTest<Fighter> {

    Personaje player1
    Personaje player2
    Minion    monster

    def setup() {
        player1  = new Personaje(name:"a", pesoMaximo: 20, xp:30, actualLife:200, baseDamage: 100, coordinate: new Coordinate(x:1, y:1))
        player2  = new Personaje(name:"b", pesoMaximo: 20, xp:30, actualLife:200, baseDamage: 100, coordinate: new Coordinate(x:1, y:1))
        monster  = new Minion(   name:"m", actualLife: 300, gold:100, maxLife: 300, baseDamage: 10, coordinate: new Coordinate(x:0,y:0))
    }

    def cleanup() {
    }

    def "A player attacks to other player and to a monster"() {
        when:
            player1.attackTo(player2)
            player1.attackTo(monster)

        then:
            player2.actualLife == 100
            monster.actualLife == 200
    }

    def "A monster attacks to other monster and to a player"() {
        when:
            monster.attackTo(player1)
            monster.attackTo(monster)

        then:
            player1.actualLife == 190
            monster.actualLife == 290
    }

    def "A weary monster attacks to a player"() {
        when:
            monster.getDamage(200)
            monster.attackTo(player1)

        then:
            player1.actualLife == 195
    }

/* To do:
Cuando un Elite queda abatido, invoca 3 monstruos tipo minion con 1⁄4 de su vida
máxima y 1⁄3 de su ataque base para que realicen un ataque contra el agresor. Esos
monstruos pasan a ser parte del dungeon.

T_odo monstruo combatiendo en un dungeon al que pertenece obtiene 1 de daño extra
por cada ítem en la tesorería.

Cada vez que un personaje derrota un monstruo, aumenta su experiencia: si el
monstruo era Elite: gana 100 de experiencia. Si el monstruo era un esclavo: 5 de
experiencia. Un minion da 25 de experiencia.
 */
}
