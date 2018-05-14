package maguitograils

import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest

class PersonajeServiceSpec extends HibernateSpec implements ServiceUnitTest<PersonajeService>{

    Fighter miniom
    Fighter cachito


    def setup() {

        cachito = new Personaje( name:"cachito", maxLife:1000,xp: 0, baseDamage:100)
        miniom = new Minion(nombre:"malo", actualLife: 1000,maxLife:200, baseDamage:1000,gold:1000)

    }

    def cleanup() {
    }

    void " un PersonajeService puede realizar un combate de un entre un pj y un ncp o pj al final de la realizacion del combate se persisten ambos con el resultado de este"() {
        given:
            cachito.save()
            miniom.save()
            def service = new PersonajeService()
        when:
            def combatResult = service.combat(cachito,miniom)

        then:
            def retador = CombatResult.findByCombatID(combatResult.id)
    }

/*    def "ttp"() {
        given:
        Integer pesoAntesDeRecogerItem = unMaguito.getPesoActual()

        when:
        unMaguito.recoger(unEscudoTorre)

        then:
        thrown(MuchoPesoException)
        unMaguito.tieneEnSuInventario(unEscudoTorre) == false
        unMaguito.getPesoActual()                    == pesoAntesDeRecogerItem
    }*/

}
