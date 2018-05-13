package maguitograils

import grails.gorm.transactions.Transactional
import maguitograils.Exception.MuchoPesoException

@Transactional
class ItemService {

    def agarrarItem(Personaje personaje, Item unItem) {
         personaje.recoger(unItem)
         personaje.save()
    }

    def equipoDeBienvenida(Personaje personaje) {
        def espadaNewbie = new Item(nombre: "Espada Newbie", peso: 1)
        personaje.recoger(espadaNewbie)
        personaje.save()
    }
}
