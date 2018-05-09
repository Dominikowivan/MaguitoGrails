package maguitograils

class Item {

    String    nombre
    int       peso
    Personaje duenio

    Item() {
    }

    static belongsTo = [duenio:Personaje]

    @Override
    String toString() {
        this.nombre
    }

    static constraints = {
        nombre     nullable:false, blank:false, unique:true
        peso       nullable:false
    }
}
