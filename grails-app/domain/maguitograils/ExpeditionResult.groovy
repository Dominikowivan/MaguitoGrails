package maguitograils

class ExpeditionResult {

    List<CombatResult> combatResults

    ExpeditionResult(List<CombatResult> aCombatResults){
        combatResults = aCombatResults
    }

    static constraints = {
    }

    def isTheWinner(Fighter player) {
        combatResults.every{ it.isTheWinner(player)}
    }
}
