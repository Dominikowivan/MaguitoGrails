package maguitograils

import grails.gorm.transactions.Transactional

@Transactional
class PersonajeService {

    def loadAllPersonajes() {
        Personaje.list()
    }

    def savePersonaje(Personaje unPersonaje) {
        unPersonaje.save()
    }

    def loadPersonaje(def p) {
        Personaje.get(p)
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
