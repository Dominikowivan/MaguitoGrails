package maguitograils

import maguitograils.Exception.MuchoPesoException

class Personaje {

    //Estructura

    String nombre
    int    pesoMaximo
    int    xp
    int    vida

    Set<Item> inventario = new HashSet<>()

    //Mapeo

    //Se declaran las constrains con las que va a mapear los atributos a la tabla
    static constraints = {
        nombre     nullable:false, blank:false, maxSize:255, unique:true
        pesoMaximo nullable:false
        xp         nullable:false
        vida       nullable:false

    }
    //Cardinalidad de las relaciones

    static hasMany = [inventario: Item]

    //otros no usados:
    //static belongsTo = [nombreColaborador:ClaseColaborador]
    //static hasOne    = [nombreColaborador:ClaseColaborador]


    //Constructor
    Personaje() {
    }

    //Metodos
    void recoger(Item item) {
        int pesoActual = this.getPesoActual()
        if (pesoActual + item.getPeso() > this.pesoMaximo) {
            throw new MuchoPesoException(this, item)
        }
        item.setDuenio(this)
        inventario.add(item)
    }

    int getPesoActual() {
        int pesoActual = 0
        inventario.each { pesoActual += it.getPeso()}
        pesoActual
    }

    @Override
    String toString() {
        this.nombre
    }

    boolean tieneEnSuInventario(Item unItem) {
        inventario.contains(unItem)
    }
}
