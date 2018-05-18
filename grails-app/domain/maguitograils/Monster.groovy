package maguitograils

class Monster extends Fighter {

    static constraints ={}

    def isWeary(){
        (maxLife / 2) >= actualLife
    }

    @Override
    def attackTo(Fighter aFighter) {
        if(isWeary()) aFighter.getDamage(attack() / 2 as int)
        else super.attackTo(aFighter)
    }



}
