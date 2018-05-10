package maguitograils

import grails.converters.JSON
import grails.test.hibernate.HibernateSpec
import grails.testing.web.controllers.ControllerUnitTest
import maguitograils.ApiAdapters.PersonajeApiAdapter

class PersonajeControllerSpec extends HibernateSpec implements ControllerUnitTest<PersonajeController> {

    Personaje       unMaguito
    Personaje       unGuerrerito
    Personaje       unCleriguito
    List<Personaje> unaListaDeLosPersonajes

    def setup(){
        unMaguito    = new Personaje(nombre: 'maguito'     , pesoMaximo: 20, xp:30, vida:200)
        unGuerrerito = new Personaje(nombre: 'guerrerito'  , pesoMaximo: 20, xp:30, vida:200)
        unCleriguito = new Personaje(nombre: 'cleriguito'  , pesoMaximo: 20, xp:30, vida:200)
        unaListaDeLosPersonajes = [unMaguito,unGuerrerito,unCleriguito]
        unaListaDeLosPersonajes.each{ it.save()}
    }

    def 'la accion index devuelve la lista de los personajes creados'() {
        given:

            //Lo que esperamos que nos devuelva la accion index es una lista con todos los personajes adaptados, como json
            def listaDeJson = unaListaDeLosPersonajes.collect{ new PersonajeApiAdapter(it)} as JSON

        when:
            controller.index()

        then:
            response.status == 200
            response.contentAsString ==  listaDeJson.toString()
    }

    def 'Dado un id, la accion show devuelve un personaje Adaptado'() {
        given:
            //Lo que esperamos que nos devuelva la accion index es una lista con todos los personajes adaptados, como json
            def maguitoJson = new PersonajeApiAdapter(unMaguito) as JSON

            //finalmente seteamos que el parametro que va a recibir
            params.id= unMaguito.getId()

        when:
            controller.show()

        then:
            response.status == 200
            response.contentAsString ==  maguitoJson.toString()
    }

    def 'Dado un id que no esta en la base de datos, la accion show tiene como respuesta 404'() {
        given:
        params.id= 444

        when:
        controller.show()

        then:
        response.status == 404
    }

    def 'Dado un personaje en el request, la accion Save lo guarda en la base de datos'() {
        given:
            def unLadroncito = new Personaje(nombre: 'ladroncito'     , pesoMaximo: 20, xp:30, vida:200)
            def unLadroncitoJson = unLadroncito as JSON
            request.setJSON(unLadroncitoJson)
            request.setMethod("POST")

        when:
            controller.save()

        then:
            response.status == 200
            Personaje.findByNombre('ladroncito') != null
    }

    def 'Dado un id, la accion delete lo elimina de la base de datos'() {
        given:
            def unMaguitoId= unMaguito.getId()
            params.id      = unMaguitoId

            request.setMethod("DELETE")

        when:
            controller.delete()

        then:
            response.status                 == 200
            Personaje.get(unMaguitoId)      == null
    }


    def 'Dado un id que no esta en la base de datos, la accion delete tiene como respuesta 404'() {
        given:
            params.id= 444
            request.setMethod("DELETE")

        when:
            controller.delete()

        then:
            response.status == 404
    }

    def 'Dado un id en los parametros y un Json en el request, la accion update cambia el estado de un objeto en la base de datos'() {
        given:
            def unMaguitoId= unMaguito.getId()
            params.id      = unMaguitoId

            def cambiosAMaguito =  JSON.parse("{'vida': 3000,'xp': 5000 }")

            request.setJSON(cambiosAMaguito)
            request.setMethod("PUT")

        when:
            controller.update()

        then:
            response.status == 200
            Personaje.get(unMaguitoId).getVida() == 3000
            Personaje.get(unMaguitoId).getXp()   == 5000
    }

    def 'Dado un id invalido en los parametros, la accion update devuelve error'() {
        given:
            params.id      = 3903809

            def cambios =  JSON.parse("{'vida': 3000,'xp': 5000 }")

            request.setJSON(cambios)
            request.setMethod("PUT")

        when:
            controller.update()

        then:
            response.status == 404

    }

}
