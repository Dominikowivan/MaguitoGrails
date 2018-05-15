package maguitograils

class MonsterCostAssociation {

    Monster monster
    Integer cost

    static constraints = {
        monster nullable: false
        cost nullable: false
    }
}
