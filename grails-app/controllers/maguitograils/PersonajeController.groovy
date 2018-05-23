package maguitograils

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import maguitograils.Exception.MuchoPesoException
import myapp.User
import org.apache.tomcat.util.net.openssl.ciphers.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder

class PersonajeController extends RestfulController<Personaje> {

    def itemService
    def personajeService
    def usuarioService
    def springSecurityService

    //nota, si queres devolver varios Jsons en un response, lo haces de la siguiente manera:
    // respond  [unObjeto: Objeto.Query(), otroObjeto: OtroObjeto.Query2()]

    // request.JSON te devuelve el json en el body de la request
    // respond es lo que vos le devolves en el body

    static responseFormats = ['json']


    PersonajeController() {
        super(Personaje)
    }


    /*

    queremos hacer un sistema de logueo para maguitoGrails


    Los usuarios deben poder loguearse todos con su usuario y password pegandole a api/login


    un usuario puede ser aventurero, dungeonero, o administrador.

    Los aventurero tienen un personaje, y pueden acceder a los metodos:

    // Http method: GET uri: /personajes/${id}
	el cual es valido solo si esta buscando el personaje que le pertenece al usuario actual

     // Http method: PUT uri: /personajes/${id}
	el cual es valido solo si esta intentando registrar el personaje que le pertenece al usuario actual


    Dungeoneros

    // Http method: POST uri: /personajes/${id}/itemDeBienvenida
    // Genera un arma generica de bienvenida en el personaje


    Administrador
    puede acceder a todo.


     */

    // Post =>  uri: /usuario
    // username: unUsername
    // password: unPassword
    // rol:      Aventurero/ Dungeonero
    // OJO: falta el unique en el username, se puede registrar multiples veces duuh
    def registrarUsuario(UserRegisterForm registerForm){
        if(!tieneErroresRegisterForm(registerForm)){
            usuarioService.registrarUsuario(registerForm)
            render status: 200
        }
        else{
            render status: 400
        }
    }

    def tieneErroresRegisterForm(UserRegisterForm userRegisterForm) {
        tieneErrores(userRegisterForm) || (userRegisterForm.rol != "Aventurero" && userRegisterForm.rol != "Dungeonero")
    }

    // Http method: GET   uri: /showPersonajes
    // Utilizamos un apiAdapter por que de afuera no quiero enviar los items ni el pesoMaximo.

    def index(){
        respond ( [personajesARenderear: personajeService.loadAllPersonajes()], view:'index' )
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
            respond  ([unPersonajeARenderear: unPersonaje], view:'show')
        }
    }

// Http method: POST uri: /personajes
    // Guarda un personaje nuevo.

    def save(Personaje unPersonaje) {
        if(!tieneErrores(unPersonaje)){
            personajeService.savePersonaje(unPersonaje)
        }
    }

    // Http method: POST uri: /usuario/personajes
    // Guarda un personaje nuevo en el usuario.

    def saveEnUsuario(Personaje unPersonaje) {
        if(!tieneErrores(unPersonaje)){
            def currentUser = springSecurityService.currentUser
            def personaje = usuarioService.savePersonaje(unPersonaje, currentUser)
            respond personaje
        }
    }

    // Http method: Get uri: /usuario/${id}

    def showPersonajeDeUsuario(Personaje unPersonaje) {
        if (unPersonaje == null || !leCorrespondeElPersonaje(unPersonaje)) {
            render status: 404
        }
        else{
            respond unPersonaje
        }
    }

    boolean leCorrespondeElPersonaje(Personaje personaje) {
        def currentUser = springSecurityService.currentUser
        personaje.duenioID == currentUser.id
    }

    // Http method: Delete uri: /deletepersonaje/${id}
    // Busca el personaje, y lo elimina

    def delete() {
        def personajeSolicitado = personajeService.loadPersonaje(params.id)
        if(personajeSolicitado== null) {
            render status: 404
        }
        else{
            personajeService.deletePersonaje(personajeSolicitado)
            render status: 200
        }

    }

    // Http method: PUT uri: /personajes/${id}
    // Busca el personaje, matchea el estado con el json y lo vuelve a guardar.

    def update(Personaje unPersonaje) {

        if(unPersonaje == null) {
            render status: 404
        }

        else if(! tieneErrores(unPersonaje)) {
            personajeService.updatePersonaje(unPersonaje.save())
            render status: 200
        }

    }



    // Http method: GET uri: /personajes/name/${alias}
    //
    // Accion definida en url mappings. El parametro que ponen despues de name/PARAMETRO lo guardas en la primera linea con params.alias
    // si en el urlmapp le pusiste un nombre a ese parametro, tenes que usar ese nombre.

    def showbyAlias() {
        def unNombre = params.alias
        def unPersonaje = personajeService.loadByNombre(unNombre)

        if (unPersonaje == null) {
            render status: 404
        }
        else {
            respond unPersonaje
        }

    }

    // Http method: POST uri: /personajes/${id}/items
    // Guarda un item nuevo en el Personaje

    def agarrarItem(Item unItem) {
        def personajeSolicitado = personajeService.loadPersonaje(params.id)

        if (!tieneErrores(unItem) && personajeSolicitado != null ){

            try{ itemService.agarrarItem(personajeSolicitado,unItem) }
            catch(MuchoPesoException e) {respond e.message  }

        }
        else{
            render status: 404
        }
    }


    // Http method: POST uri: /dungeoneros/${id}/itemDeBienvenida
    // Genera un arma generica de bienvenida en el personaje
    def nuevoEquipamiento() {

        def personajeSolicitado = personajeService.loadPersonaje(params.id)
        if(personajeSolicitado== null) {
            render status: 404
        }

        else {
            try{ itemService.equipoDeBienvenida(personajeSolicitado) }
            catch(MuchoPesoException e) {respond e.message  }
        }
    }

    def tieneErrores(Object unObjeto){
        def hayErrores = unObjeto.hasErrors()

        if(hayErrores) {
            respond unObjeto.errors
        }

        hayErrores
    }

}