package maguitograils

import grails.gorm.transactions.Transactional

@Transactional
class DungeonService {

    def buyMonster(MonsterMarket market, Dungeon dungeon, Monster monster) {

        def persistedDungeon = Dungeon.findByName(dungeon.name)

        def monsterToBuy = market.monstersCatalogue.find{m -> m.monster == monster}
        //Si ya se es horrible, lo quise dejar funcionando por lo menos :B
        if (monsterToBuy != null) {
            if (monsterToBuy.cost <= persistedDungeon.gold) {
                persistedDungeon.add(monster)
                persistedDungeon.setGold(dungeon.gold - monsterToBuy.cost)
                persistedDungeon.save()

                //TODO: Ver si tiene sentido implementar esta clase
                //      No estoy muy seguro de como testear el retorno de este mensaje y no llegue a ver como hacerlo

                //return new MonsterBuyReceipt(monster: monster, paid: monsterToBuy.cost, buyer: dungeon, seller: market)
            }
        }
        else {
            println("No se pudo comprar el monstruo")
            //TODO: Me gustaria ver como hacer y testear Exceptions con groovy
            //      No lo hice por falta de tiempo, pero estaria verlo el miercoles, es algo que puede ser muy util
        }
    }

    def buyMonsterOwnedByDungeon(Dungeon buyerDungeon, Dungeon sellerDungeon, Monster monster, Integer offeredGold) {

        def persistedBuyerDungeon = Dungeon.findByName(buyerDungeon.name)
        def persistedSellerDungeon = Dungeon.findByName(sellerDungeon.name)

        if (offeredGold <= persistedBuyerDungeon.gold && persistedSellerDungeon.monsters.contains(monster)) {
            persistedBuyerDungeon.add(monster)
            persistedSellerDungeon.monsters.remove(monster)
            persistedBuyerDungeon.setGold(persistedBuyerDungeon.gold - offeredGold)
            persistedSellerDungeon.setGold(persistedSellerDungeon.gold + offeredGold)
            persistedBuyerDungeon.save()
            persistedSellerDungeon.save()
        }
        else {
            println("La Dungeon compradora no tiene oro suficiente para realizar la compra")
        }
    }
}
