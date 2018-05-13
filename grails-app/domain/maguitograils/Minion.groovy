package maguitograils

class Minion extends Monster {

    String          name
    Integer         gold


    static constraints ={}
    def getMyMoney() {
        def result = gold
        gold = 0
        result
    }
    @Override
    def isWeary(){ (maxLife / 2) >= actualLife }

    @Override
    def addGold(Integer aGold) {
        gold += aGold
    }
}

