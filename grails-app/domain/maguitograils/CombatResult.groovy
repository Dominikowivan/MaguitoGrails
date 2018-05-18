package maguitograils

class CombatResult {

    List<Turn> turns = []
    Personaje winner

    static constraints = {
    }

    def add(Turn turn) {
        turns.add(turn)
    }

    def winner(Personaje aPlayer, Personaje otherPlayer) {
        if(aPlayer.actualLife > otherPlayer.actualLife){
            winner = aPlayer
        }
        winner = otherPlayer
    }
}