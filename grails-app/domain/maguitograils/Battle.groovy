package maguitograils

class Battle {

    static constraints = {}

    Battle(){}

    def start(Personaje aDefiant, Personaje aDefender) {

        def combatResult = new CombatResult()

        def attacker = aDefiant
        def damaged  = aDefender

        while(aDefiant.vida != 0 && aDefender.vida !=0) {
            damaged.getDamage(attacker.attack())

            def turnFighter = new Turn(attackerName: attacker.nombre, damagedName: damaged.nombre,
                    attackerLife: attacker.vida, damagedLife: damaged.vida)
            combatResult.add(turnFighter)

            def change = attacker
            attacker   = damaged
            damaged    = change
        }
        combatResult.winner(attacker, damaged)
        combatResult
    }

}