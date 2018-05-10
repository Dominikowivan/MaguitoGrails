package maguitograils

import grails.test.hibernate.HibernateSpec


class TestingGroundSpec extends HibernateSpec{

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
            //no hay "perdida de identidad". Nunca se salio de la sesion.
            unMaguito == elMismoMaguito
    }

    def "Se guarda un objeto en cascada"() {

        given:
            unMaguito.recoger(unBaculo)
            unMaguito.save()

        when:
            Item elMismoBaculo = Item.findByNombre("baculo")

        then:
            unBaculo.equals(elMismoBaculo)
    }

    def "Se guarda un objeto"() {

        given:
        unBaculo.save()

        when:
        Item elMismoBaculo = Item.findByNombre("baculo")

        then:
        unBaculo.equals(elMismoBaculo)
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


    def "Se obtienen todos los objetos guardados"() {

        given:
        def items = [unBaculo, unSombrero, unEscudoTorre]
        items.each{it.save()}

        when:
        def itemsBuscados = Item.list()

        then:
        itemsBuscados.containsAll(items)
    }

    def "Se testea el uso de un mock"() {

        given:
            //Declaramos el mock
            def mockPersonaje = Mock(Personaje)

            //Declaramos que cuando recibe este mensaje, devuelve 5000
            mockPersonaje.getPesoActual() >> 5000


        when:

            mockPersonaje.recoger(unBaculo)
            mockPersonaje.recoger(unBaculo)

        then:
            //Como declaramos arriba, el metodo deveria devolver 5000
            5000 == mockPersonaje.getPesoActual()
            //Asi asertamos que el mensaje se recibio 2 veces
            2 * mockPersonaje.recoger(unBaculo)

    }

}
