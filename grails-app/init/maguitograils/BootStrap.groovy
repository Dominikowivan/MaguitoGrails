package maguitograils

class BootStrap {

    def init = { servletContext ->

        def unBaculo      = new Item     (nombre: "baculo"     , peso: 10).save()
        def unSombrero    = new Item     (nombre: "sombrero"   , peso: 5 ).save()
        def unEscudoTorre = new Item     (nombre: "escudoTorre", peso: 25).save()
        def unPersonaje   = new Personaje(name:"maguito"     , pesoMaximo: 20, xp:30, actualLife:200)
        unPersonaje  .grab(unBaculo)
        unPersonaje  .save()

    }
    def destroy = {
    }
}
