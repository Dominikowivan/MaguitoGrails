package maguitograils

import maguitograils.Exception.MuchoPesoException

class Personaje extends Fighter{

    //Estructura

    Integer     pesoMaximo
    Integer     xp


    Set<Item> inventario = new HashSet<>()

    //Mapeo

    //Se declaran las constrains con las que va a mapear los atributos a la tabla
    //mas info sobre constraints: https://docs.grails.org/latest/ref/Constraints/Usage.html
    static constraints = {
        pesoMaximo      nullable:false
        xp              nullable:false
    }
    //Cardinalidad de las relaciones

    //oneToMany
    static hasMany = [inventario: Item]

    //otros no usados:
    //
    //static belongsTo = [nombreColaborador:ClaseColaborador]
    //oneToOne
    //static hasOne    = [nombreColaborador:ClaseColaborador]
    // mas info sobre relaciones:
    //https://docs.grails.org/latest/ref/Domain%20Classes/Usage.html

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
        this.name
    }

    boolean tieneEnSuInventario(Item unItem) {
        inventario.contains(unItem)
    }
}
