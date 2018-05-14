package maguitograils

class Battle {

    static constraints = {}

    def start(Fighter aDefiant, Fighter aDefender) {
        def combatResult       = new CombatResult()
        def turnActual         = aDefiant
        def notTurn            = aDefender
        while(aDefiant.actualLife != 0 && aDefender.actualLife !=0){
            notTurn.getDamage(turnActual.attack())

            def turnFighter = new Turn(turnActual.actualLife,notTurn.actualLife,turnActual.attack())

            combatResult.add(turnFighter)
        }
    }
}
