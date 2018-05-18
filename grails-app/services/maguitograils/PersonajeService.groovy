package maguitograils

import grails.gorm.transactions.Transactional
import maguitograils.Exception.TheyArentInTheSameCoordinateException

@Transactional
class PersonajeService {

    // Mas info sobre mensajes de persistencia:  //https://docs.grails.org/latest/ref/Domain%20Classes/Usage.html

    def combat(Personaje defiant, Personaje defender) {
        if(theyAreInTheSameCoordinate(defiant, defender)){
            throw new TheyArentInTheSameCoordinateException(defiant, defender)
        }
        def combat          = new Battle()
        def combatResult    = combat.start(defiant, defender)
        updatePersonaje(defiant)
        updatePersonaje(defender)
        combatResult.save()
        combatResult
    }

    def theyAreInTheSameCoordinate(Personaje personaje1, Personaje personaje2) {
        personaje1.coordinate.isEquals(personaje2.coordinate)
    }

    def move(Personaje unPersonaje, Coordinate unaCoordenada) {
        def personaje = loadByNombre(unPersonaje.nombre)
        personaje.coordinate = unaCoordenada
        updatePersonaje(personaje)
    }

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
