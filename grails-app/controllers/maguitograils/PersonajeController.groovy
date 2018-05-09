package maguitograils

import grails.gorm.transactions.Transactional
import grails.rest.RestfulController
import maguitograils.ApiAdapters.PersonajeApiAdapter
import maguitograils.Exception.MuchoPesoException

class PersonajeController extends RestfulController<Personaje> {

    ItemService itemService

    //nota, si queres devolver varios Jsons en un response, lo haces de la siguiente manera:
    // respond  [unObjeto: Objeto.Query(), otroObjeto: OtroObjeto.Query2()]

    static responseFormats = ['json']

    PersonajeController() {
        super(Personaje)
        itemService = new ItemService()
    }

    // Http method: GET   uri: /personajes
    // Utilizamos un apiAdapter por que de afuera no quiero enviar los items ni el pesoMaximo.
    def index(){
        def personajes = Personaje.list().collect{ new PersonajeApiAdapter(it)}
        respond personajes
    }


    // Http method: GET uri: /personajes/${id}
    //
    // En cualquiera de las acciones, si especificas como parametro un objeto de dominio, Grails asume que lo que esta recibiendo
    // desde el get es una id, y va automaticamente a buscarlo por esa id.
    // Si no existe un objeto con esa id, setea entonces ese parametro como null.

    // Utilizamos un apiAdapter por que de afuera no quiero enviar los items ni el pesoMaximo.
    def show(Personaje unPersonaje) {
        if (unPersonaje == null) {
            render status: 404
        }
        else {
            respond new PersonajeApiAdapter(unPersonaje)
        }

    }

    // Http method: POST uri: /personajes
    // Guarda un personaje nuevo.
    @Transactional
    def save(Personaje unPersonaje) {
        if(unPersonaje.hasErrors()) {
            respond unPersonaje.errors
        }
        else {
            unPersonaje.save()
        }
    }

    // Http method: Delete uri: /personajes/${id}
    // Busca el personaje, y lo elimina
    @Transactional
    def delete() {
        def personajeSolicitado = Personaje.get(params.id)
        if(personajeSolicitado== null) {
            render status: 404
        }
        else{
            personajeSolicitado.delete()
            render status: 200
        }

    }

    // Http method: PUT uri: /personajes/${id}
    // Busca el personaje, matchea el estado con el json y lo vuelve a guardar.
    @Transactional
    def update(Personaje unPersonaje) {

        if(unPersonaje.hasErrors()) {
            respond unPersonaje.errors
        }

        if(unPersonaje == null) {
            render status: 404
        }

        else{
            unPersonaje.save()
            render status: 200
        }

    }

    // Http method: GET uri: /personajes/name/${alias}
    //
    // Accion definida en url mappings. El parametro que ponen despues de name/PARAMETRO lo guardas en la primera linea con params.alias
    // si en el urlmapp le pusiste un nombre a ese parametro, tenes que usar ese nombre.
    def showbyAlias() {
        def unNombre = params.alias
        def unPersonaje = Personaje.findByNombre(unNombre)

        if (unPersonaje == null) {
            render status: 433
        }
        else {
            respond unPersonaje
        }

    }


    // Http method: POST uri: /personajes/${id}/items
    // Guarda un item nuevo en el Personaje
    @Transactional
    def agarrarItem(Item unItem) {

        if(unItem.hasErrors()) {
            respond unItem.errors
        }

        def personajeSolicitado = Personaje.get(params.id)
        if(personajeSolicitado== null) {
            render status: 404
        }

        else {
            try{ itemService.agarrarItem(personajeSolicitado,unItem) }
            catch(MuchoPesoException e) {respond e.message  }
        }
    }

    // Http method: POST uri: /personajes/${id}/itemDeBienvenida
    // Genera un arma generica de bienvenida en el personaje
    @Transactional
    def nuevoEquipamiento() {

        def personajeSolicitado = Personaje.get(params.id)
        if(personajeSolicitado== null) {
            render status: 404
        }

        else {
            try{ itemService.equipoDeBienvenida(personajeSolicitado) }
            catch(MuchoPesoException e) {respond e.message  }
        }
    }

}