package maguitograils

abstract class  Monster  extends Fighter{

    static constraints ={}
    static hasOne    = [coordinate:Coordinate]


    abstract def isWeary()

    def move(Coordinate aCoordinate){coordinate = aCoordinate}

    def addGold(Integer aGold){}
}
