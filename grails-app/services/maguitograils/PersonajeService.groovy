package maguitograils

import grails.gorm.transactions.Transactional
import maguitograils.Exception.TheyArentInTheSameCoordinateException

@Transactional
class PersonajeService {

    // Mas info sobre mensajes de persistencia:  //https://docs.grails.org/latest/ref/Domain%20Classes/Usage.html

    def combat(Fighter defiant, Fighter defender) {
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

    def theyAreInTheSameCoordinate(Fighter personaje1, Fighter personaje2) {
        personaje1.coordinate.isEquals(personaje2.coordinate)
    }

    def move(Personaje unPersonaje, Coordinate unaCoordenada) {
        def personaje = loadByNombre(unPersonaje.name)
        personaje.coordinate = unaCoordenada
        updatePersonaje(personaje)
    }

    def exploreDungeon(Fighter player, Dungeon dungeon) {
        def dungeonResult    = dungeon.monsters.collect{combat(player, it)}
        def expeditionResult = new ExpeditionResult(dungeonResult)
        if(expeditionResult.isTheWinner(player)) dungeon.giveVictory(player)
        else dungeon.getVictory(player)
        updatePersonaje(player)
        dungeon.save()
        expeditionResult
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

    def updatePersonaje(Fighter unPersonaje) {
        unPersonaje.save()
    }

    def loadByNombre(String unNombre) {
        Personaje.findByName(unNombre)
    }


}
