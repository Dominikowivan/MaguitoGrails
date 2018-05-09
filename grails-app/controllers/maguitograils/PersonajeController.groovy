package maguitograils

import grails.gorm.transactions.Transactional
import grails.rest.RestfulController

class PersonajeController extends RestfulController<Personaje> {

    //nota, si queres devolver varios Jsons en un response, lo haces de la siguiente manera:
    // respond  [unObjeto: Objeto.Query(), otroObjeto: OtroObjeto.Query2()]

    static responseFormats = ['json']

    PersonajeController() {
        super(Personaje)
    }

    // Http method: GET   uri: /personajes
    // devuelve todos los personajes
    def index(){
        respond Personaje.list()
    }

    // Http method: GET uri: /personajes/${id}
    // Especificando un objeto de dominio como el parametro de la accion, grails va a buscar la instancia autmaticamente por el id
    // Si no existe un objeto con esa ide, entonces devuelve 404
     def show(Personaje unPersonaje) {
        if(unPersonaje == null) {
            render status:404
        }
        else {

            respond unPersonaje
        }

    }

    // Http method: POST uri: /personajes
    // Guarda un personaje nuevo
    @Transactional
    def save(Personaje unPersonaje) {
        if(unPersonaje.hasErrors()) {
            respond unPersonaje.errors
        }
        else {
          unPersonaje.save()
        }
    }

    // Http method: PUT uri: /personajes/${id}
    // Busca el personaje, matchea el estado con el json y lo vuelve a guardar.
    @Transactional
    def update(Personaje unPersonaje) {

        if(unPersonaje.hasErrors()) {
            respond unPersonaje.errors
        }

        Personaje personajeSolicitado = Personaje.findByNombre(unPersonaje.nombre)

        if(personajeSolicitado == null) {
            render status: NOT_FOUND
        }
        else {
            personajeSolicitado.setInventario(unPersonaje.getInventario())
            personajeSolicitado.setVida      (unPersonaje.getVida()      )
            personajeSolicitado.setXp        (unPersonaje.getXp()        )
            personajeSolicitado.setPesoMaximo(unPersonaje.getPesoMaximo())
        }
    }

    // Http method: Delete uri: /personajes/${id}
    // Busca el personaje, y lo elimina
    @Transactional
    def delete(Personaje unPersonaje) {

        if(unPersonaje.hasErrors()) {
            respond unPersonaje.errors
        }

        Personaje personajeSolicitado = Personaje.findByNombre(unPersonaje.nombre)

        if(personajeSolicitado == null) {
            render status: NOT_FOUND
        }
        else {
            Personaje.deleteAll(personajeSolicitado)
        }
    }


}
