package maguitograils.ApiAdapters

import maguitograils.Personaje

class PersonajeApiAdapter {

    String nombre
    int    xp
    int    vida

    PersonajeApiAdapter(Personaje unPersonaje){
        nombre = unPersonaje.getNombre()
        xp     = unPersonaje.getXp()
        vida   = unPersonaje.getVida()
    }

}
