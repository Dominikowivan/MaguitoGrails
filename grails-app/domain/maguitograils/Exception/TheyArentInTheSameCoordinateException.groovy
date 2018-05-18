package maguitograils.Exception

import maguitograils.Fighter

class TheyArentInTheSameCoordinateException extends RuntimeException {

    private Fighter personaje1
    private Fighter personaje2

    TheyArentInTheSameCoordinateException(Fighter unPersonaje1, Fighter unPersonaje2) {
        personaje1 = unPersonaje1
        personaje2 = unPersonaje2
    }

    @Override
    String getMessage() {
        personaje1.name + " y " + personaje2.name + " no estan en la misma coordenada"
    }

}