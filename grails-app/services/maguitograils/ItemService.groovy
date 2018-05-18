package maguitograils

import grails.gorm.transactions.Transactional

@Transactional
class ItemService {

    def agarrarItem(Personaje personaje, Item unItem) {
         personaje.grab(unItem)
         personaje.save()
    }

    def equipoDeBienvenida(Personaje personaje) {
        def espadaNewbie = new Item(nombre: "Espada Newbie", peso: 1)
        personaje.grab(espadaNewbie)
        personaje.save()
    }
}
