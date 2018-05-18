package maguitograils

import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest
import maguitograils.Exception.TheyArentInTheSameCoordinateException

class PersonajeServiceSpec extends HibernateSpec implements ServiceUnitTest<PersonajeService> {

    Personaje personaje1
    Personaje personaje2
    PersonajeService ps

    def setup(){
        personaje1  = new Personaje(nombre:"a", pesoMaximo: 20, xp:30, vida:200, baseDamage: 100, coordinate: new Coordinate(x:1, y:1))
        personaje2  = new Personaje(nombre:"b", pesoMaximo: 20, xp:30, vida:200, baseDamage: 100, coordinate: new Coordinate(x:2, y:2))
        ps          = new PersonajeService()
    }

    def 'A player combats to another'() {
        given:
            ps.savePersonaje(personaje1)
            ps.savePersonaje(personaje2)

        when:
            def combatResult = ps.combat(personaje1, personaje2)

        then:
            def p1 = ps.loadByNombre(personaje1.nombre)
            def p2 = ps.loadByNombre(personaje2.nombre)
            p1.vida == 100
            p2.vida == 0
            combatResult.turns.size() == 3
            combatResult.winner == personaje1
    }

    def "Two players can't combat because they aren't in the same coordinate"() {
        given:
            personaje2.setCoordinate(new Coordinate(x:1, y:1))
            ps.savePersonaje(personaje1)
            ps.savePersonaje(personaje2)

        when:
            ps.combat(personaje1, personaje2)

        then:
            thrown(TheyArentInTheSameCoordinateException)
    }

    def 'A player changes his coordinate'() {
        given:
            ps.savePersonaje(personaje1)
            def newCoordinate = new Coordinate(x: 6, y: 6)

        expect:
            def aPlayer = ps.loadByNombre(personaje1.nombre)
            aPlayer.coordinate.isEquals(personaje1.coordinate)

        when:
            ps.move(personaje1, newCoordinate)

        then:
            def player = ps.loadByNombre(personaje1.nombre)
            player.coordinate.isEquals(newCoordinate)
    }

}
