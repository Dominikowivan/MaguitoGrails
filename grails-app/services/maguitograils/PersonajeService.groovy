package maguitograils

import grails.gorm.transactions.Transactional

@Transactional
class PersonajeService {

    // Mas info sobre mensajes de persistencia:  //https://docs.grails.org/latest/ref/Domain%20Classes/Usage.html

    def loadAllPersonajes() {
        Personaje.list()
    }

    def savePersonaje(Personaje unPersonaje) {
        unPersonaje.save()
    }

    def loadPersonaje(def personajeID) {
        Personaje.get(personajeID)
    }

    def deletePersonaje(Personaje unPersonaje) {
        unPersonaje.delete()
    }

    def updatePersonaje(Personaje unPersonaje) {
        unPersonaje.save()
    }

    def loadByNombre(def unNombre) {
        Personaje.findByNombre(unNombre)

    }
}
