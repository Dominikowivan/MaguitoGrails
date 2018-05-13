package maguitograils

import grails.testing.gorm.DomainUnitTest
import maguitograils.Exception.MuchoPesoException
import spock.lang.Specification

class PersonajeSpec extends Specification implements DomainUnitTest<Personaje> {

    //Los tests se escriben con el framework: Spock
    //Informacion de dicho framework se puede encontrar aca: http://www.baeldung.com/groovy-spock

    Personaje unMaguito
    Item      unBaculo
    Item      unSombrero
    Item      unEscudoTorre

    def setup() {
        unMaguito     = new Personaje(nombre:"maguito"     , pesoMaximo: 20, xp:30, vida:200)
        unBaculo      = new Item     (nombre: "baculo"     , peso: 10)
        unSombrero    = new Item     (nombre: "sombrero"   , peso: 5 )
        unEscudoTorre = new Item     (nombre: "escudoTorre", peso: 25)
    }


    def "Un maguito recoge un item. Sabe que se encuentra en su inventario y su peso aumenta acorde al peso del item"() {
        //Esto es el setup
        given:
            Integer pesoAntesDeRecogerItem = unMaguito.getPesoActual()
            Integer pesoDelBaculo          = unBaculo.getPeso()
            Integer pesoEsperado           = pesoDelBaculo + pesoAntesDeRecogerItem
        //Excercise
        when:
            unMaguito.recoger(unBaculo)

        //Assertion (then: o expect:)
        then:
            unMaguito.tieneEnSuInventario(unBaculo) == true
            unBaculo.getDuenio()                    == unMaguito
            unMaguito.getPesoActual()               == pesoEsperado
    }

    def "Un maguito intenta recoger un item que exde su peso y se lanza un error"() {
        given:
            Integer pesoAntesDeRecogerItem = unMaguito.getPesoActual()

        when:
            unMaguito.recoger(unEscudoTorre)

        then:
            thrown(MuchoPesoException)
            unMaguito.tieneEnSuInventario(unEscudoTorre) == false
            unMaguito.getPesoActual()                    == pesoAntesDeRecogerItem
    }

}
