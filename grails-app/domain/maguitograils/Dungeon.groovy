package maguitograils

class Dungeon {

    String              name
    ArrayList<Minion>   monsters = []
    int                 gold
    Coordinate          coordinate

    static constraints = {
        name        nullable:false, blank:false, unique:true
        //monsters    nullable:true
        gold        nullable:false
        coordinate  nullable:false
    }

    int monstersWeary() {monsters.count {it.isWeary()}}

    def add(Minion monster) {monsters.add(monster) }

    def recollectGold() {
        monsters.each { it.move(coordinate)}
        gold += this.getMonstersTotalGold()
    }

    def getMonstersTotalGold() { monsters.sum{it.getMyMoney()} }

    def countMonster(){ monsters.size() }

    def giveVictory(Fighter player) {
        int allGold = monsters.sum {
            int aGold = it.gold
            it.gold = 0
            aGold
        } as int
        player.addGold(gold + allGold)
        gold = 0
    }

    def getVictory(Fighter player) {
        gold += player.gold
        player.gold = 0
    }
}
