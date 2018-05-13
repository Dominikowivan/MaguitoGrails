package maguitograils

class BootStrap {

    def init = { servletContext ->

        def unBaculo      = new Item     (nombre: "baculo"     , peso: 10).save()
        def unSombrero    = new Item     (nombre: "sombrero"   , peso: 5 ).save()
        def unEscudoTorre = new Item     (nombre: "escudoTorre", peso: 25).save()
        def unPersonaje   = new Personaje(nombre:"maguito"     , pesoMaximo: 20, xp:30, vida:200)
        unPersonaje  .recoger(unBaculo)
        unPersonaje  .save()

    }
    def destroy = {
    }
}
