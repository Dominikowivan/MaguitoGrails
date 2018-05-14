package maguitograils

abstract class Fighter {
    String          name
    Integer         actualLife
    Integer         maxLife
    Coordinate      coordinate
    Integer         baseDamage

    static constraints ={
        name           nullable:false, blank:false, maxSize:255, unique:true
        actualLife     nullable:false
        maxLife        nullable:false
        coordinate     nullable:false
        baseDamage     nullable:false
    }

    def attack(Integer aAttack){
        baseDamage
    }
    def getDamage(Integer aDamage) {actualLife -= aDamage}

}
