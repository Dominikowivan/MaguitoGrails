package maguitograils

abstract class  Monster {

    Integer         actualLife
    Integer         maxLife
    Coordinate      coordinate

    static constraints ={}

    def getDamage(Integer aDamage) {actualLife -= aDamage}

    abstract def isWeary()

    def move(Coordinate aCoordinate){coordinate = aCoordinate}

    def addGold(Integer aGold) {}


}
