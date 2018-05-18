package maguitograils

class CombatResult {

    List<Turn> turns = []
    Fighter winner

    static constraints = {
    }

    def add(Turn turn) {
        turns.add(turn)
    }

    def winner(Fighter aPlayer, Fighter otherPlayer) {
        if(aPlayer.actualLife > otherPlayer.actualLife){
            winner = aPlayer
        }
        winner = otherPlayer
    }

    def isTheWinner(Fighter player) {
        winner.name == player.name
    }
}