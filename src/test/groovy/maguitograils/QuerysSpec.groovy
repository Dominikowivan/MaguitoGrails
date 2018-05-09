package maguitograils

import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class QuerysSpec extends HibernateSpec{

    //Notese que la superclase es HibernateSpec, esto nos permite testear utilizando la base y abstraernos
    //De iniciar sesiones.

    Personaje     unMaguito
    Item          unBaculo
    Item          unSombrero
    Item          unEscudoTorre

    def setup() {
        unMaguito     = new Personaje(nombre:"maguito"     , pesoMaximo: 20, xp:30, vida:200)
        unBaculo      = new Item     (nombre: "baculo"     , peso: 10)
        unSombrero    = new Item     (nombre: "sombrero"   , peso: 5 )
        unEscudoTorre = new Item     (nombre: "escudoTorre", peso: 25)
    }

    def cleanup() {
    }

    def "Al guardar y luego recuperar se obtienen objetos similares"() {

        when:
            unMaguito.recoger(unBaculo)
            unMaguito.save()

        then:
            Personaje elMismoMaguito = Personaje.findByNombre("maguito")

            unMaguito.getNombre()           == elMismoMaguito.getNombre()
            unMaguito.getPesoMaximo()       == elMismoMaguito.getPesoMaximo()
            unMaguito.getVida()             == elMismoMaguito.getVida()
            unMaguito.getXp()               == elMismoMaguito.getXp()
            unMaguito.getInventario().size()== elMismoMaguito.getInventario().size()

            //son el mismo objeto =)!
            //no hay "perdida de identidad". Nunca se salio nunca de la sesion.
            unMaguito == elMismoMaguito
    }

    def "Se obtienen todos los objetos guardados"() {

        given:
            def items = [unBaculo, unSombrero, unEscudoTorre]
            items.each{it.save()}

        when:
            def itemsBuscados = Item.list()

        then:
            itemsBuscados.containsAll(items)
    }

    def "Se obtienen todos los objetos en el inventario del maguito"() {

        given:
            def items = [unBaculo, unSombrero]
            items.each{unMaguito.recoger(it)}
            unMaguito.save()

        when:
            def itemsBuscados = Item.findAllByDuenio(unMaguito)

        then:
            itemsBuscados.containsAll(items)
            itemsBuscados.contains(unEscudoTorre) == false
    }


}
