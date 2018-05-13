package maguitograils

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class MinionSpec extends Specification implements DomainUnitTest<Minion> {

    Minion charango


    def setup() {
        charango= new Minion(name:"Charangito", actualLife: 300, gold:100, maxLife: 300, coordinate: new Coordinate(x:0,y:0))
    }

    def cleanup() {
    }

    void "Un moustruo con su vida competa no esta abatido"(){
        expect: !(charango.isWeary())
    }
    void "un monstruo full vida lo atacan y le bajan 100 de vida y ahora solo le queda 200"() {
        //Esto es el setup
        when:
            charango.getDamage(100)

        //Assertion (then: o expect:)
        then:
            charango.actualLife == 200
    }
    void "Un monstruo con su vida completa es abatido tras recibir un golpe de 200 puntos"(){
        when: charango.getDamage(200)
        then:
            charango.actualLife == 100
            charango.isWeary()
    }

    void "un monstruo que inicialmente estaba en la cordenada x=0 y=0 se desplaza a la cordenada x=10y=20"(){
        expect: charango.coordinate == new Coordinate(x:0,y:0)
        when:   charango.move(new Coordinate(x:10,y:20))
        then:   charango.coordinate == new Coordinate(x:10,y:20)
    }






}
