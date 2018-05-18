package maguitograils.Exception

import maguitograils.Personaje

class TheyArentInTheSameCoordinateException extends RuntimeException {

    private Personaje personaje1
    private Personaje personaje2

    TheyArentInTheSameCoordinateException(Personaje unPersonaje1, Personaje unPersonaje2) {
        personaje1 = unPersonaje1
        personaje2 = unPersonaje2
    }

    @Override
    String getMessage() {
        personaje1.nombre + " y " + personaje2.nombre + " no estan en la misma coordenada"
    }

}