package maguitograils

import maguitograils.Exception.MuchoPesoException

class Personaje {

    //Estructura

    String nombre
    int    pesoMaximo
    int    xp
    int    vida
    int    maxLife
    int    baseDamage
    Coordinate coordinate = new Coordinate(x:0, y:0)

    Set<Item> inventario = new HashSet<>()

    //Mapeo

    //Se declaran las constrains con las que va a mapear los atributos a la tabla
    //mas info sobre constraints: https://docs.grails.org/latest/ref/Constraints/Usage.html
    static constraints = {
        nombre     nullable:false, blank:false, maxSize:255, unique:true
        pesoMaximo nullable:false
        xp         nullable:false
        vida       nullable:false
        maxLife    nullable:false
        baseDamage nullable:false
        coordinate nullable: false
    }
    //Cardinalidad de las relaciones

    static hasMany = [inventario: Item]

    //otros no usados:
    //static belongsTo = [nombreColaborador:ClaseColaborador]
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
        this.nombre
    }

    boolean tieneEnSuInventario(Item unItem) {
        inventario.contains(unItem)
    }

    def attack(){
        baseDamage
    }
    def getDamage(int aDamage) {vida -= aDamage}
}
