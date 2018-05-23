package maguitograils

import myapp.Role
import myapp.User
import myapp.UserRole

class BootStrap {

    //Se declara el servicio de seguridad
    def springSecurityService

    def init = { servletContext ->

        // Creamos y salvamos unos objetos mocks
        def unBaculo      = new Item     (nombre: "baculo"     , peso: 10).save()
        def unSombrero    = new Item     (nombre: "sombrero"   , peso: 5 ).save()
        def unEscudoTorre = new Item     (nombre: "escudoTorre", peso: 25).save()
        def unPersonaje   = new Personaje(nombre:"maguito"     , pesoMaximo: 20, xp:30, vida:200)
        unPersonaje  .recoger(unBaculo)
        unPersonaje  .save()

        // Salvamos en la base de datos un nuevo rol y un Actor
        def userRole  = new Role(authority:'ROLE_AVENTURERO_USER').save()
        def userRole2 = new Role(authority:'ROLE_DUNGEONERO_USER').save()
        def userRole3 = new Role(authority:'ROLE_ADMIN_USER').save()

        def pepita   = new User(username:'pepita',password: 'vuela', personajes:[]).save()
        def dionisia = new User(username:'dionisia',password: 'vuela', personajes:[]).save()
        def azul     = new User(username:'azul',password: 'vuela', personajes:[]).save()

        UserRole.create(pepita  , userRole )
        UserRole.create(dionisia, userRole2)
        UserRole.create(azul    , userRole3)

        UserRole.withSession {
            it.flush()
            it.clear()
        }

    }
    def destroy = {
    }
}
