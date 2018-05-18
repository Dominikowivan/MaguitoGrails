package maguitograils

abstract class Fighter {

    String     name
    int        actualLife
    int        maxLife
    int        baseDamage
    int        gold
    Coordinate coordinate = new Coordinate(x: 0, y: 0)

    static constraints = {
        name       nullable:false, blank:false, maxSize:255, unique:true
        actualLife nullable:false
        maxLife    nullable:false
        baseDamage nullable:false
        gold       nullable:false
        coordinate nullable:false
    }

    def move(Coordinate aCoordinate){coordinate = aCoordinate}

    def attackTo(Fighter aFighter) {
        aFighter.getDamage(attack())
    }

    def attack(){
        baseDamage
    }

    def getDamage(int aDamage) {
        actualLife -= Math.min(aDamage, actualLife)
    }

    def getMyMoney() {
        def result = gold
        gold = 0
        result
    }

    def addGold(int aGold) {
        gold += aGold
    }
}
