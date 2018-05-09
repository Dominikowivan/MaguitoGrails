package maguitograils.Exception

import maguitograils.Item
import maguitograils.Personaje

class MuchoPesoException extends RuntimeException {

    private final Personaje personaje
    private final Item item

    MuchoPesoException(Personaje personaje, Item item) {
        this.personaje = personaje
        this.item = item
    }

    @Override
    String getMessage() {
        "El personaje [" + this.personaje + "] no puede recoger [" + this.item + "] porque carga mucho peso ya"
    }

}
