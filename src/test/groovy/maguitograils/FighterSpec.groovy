package maguitograils

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class FighterSpec extends Specification implements DomainUnitTest<Fighter> {

    Personaje player1
    Personaje player2
    Minion    monster

    def setup() {
        player1  = new Personaje(name:"a", pesoMaximo: 20, xp:30, actualLife:200, baseDamage: 100, coordinate: new Coordinate(x:1, y:1))
        player2  = new Personaje(name:"a", pesoMaximo: 20, xp:30, actualLife:200, baseDamage: 100, coordinate: new Coordinate(x:1, y:1))
        monster  = new Minion(name:"Charangito", actualLife: 300, gold:100, maxLife: 300, coordinate: new Coordinate(x:0,y:0))

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

/*
1 personaje debe poder atacar a otro personaje o monstruo, realizando como daño su
ataque base + el daño adicional que le proporciona su arma

1 monstruo también debe poder atacar a otro monstruo o personaje, realizando como
daño su ataque base.

1 monstruo que está abatido, hace la mitad de daño del que haría normalmente.

Cuando un Elite queda abatido, invoca 3 monstruos tipo minion con 1⁄4 de su vida
máxima y 1⁄3 de su ataque base para que realicen un ataque contra el agresor. Esos
monstruos pasan a ser parte del dungeon.

T odo monstruo combatiendo en un dungeon al que pertenece obtiene 1 de daño extra
por cada ítem en la tesorería.

Cada vez que un personaje derrota un monstruo, aumenta su experiencia: si el
monstruo era Elite: gana 100 de experiencia. Si el monstruo era un esclavo: 5 de
experiencia. Un minion da 25 de experiencia.
 */
}
