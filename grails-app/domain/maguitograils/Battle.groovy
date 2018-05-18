package maguitograils

class Battle {

    static constraints = {}

    Battle(){}

    def start(Personaje aDefiant, Personaje aDefender) {

        def combatResult = new CombatResult()

        def attacker = aDefiant
        def damaged  = aDefender

        while(aDefiant.actualLife != 0 && aDefender.actualLife !=0) {
            attacker.attackTo(damaged)

            def turnFighter = new Turn(attackerName: attacker.name, damagedName: damaged.name,
                    attackerLife: attacker.actualLife, damagedLife: damaged.actualLife)
            combatResult.add(turnFighter)

            def change = attacker
            attacker   = damaged
            damaged    = change
        }
        combatResult.winner(attacker, damaged)
        combatResult
    }

}