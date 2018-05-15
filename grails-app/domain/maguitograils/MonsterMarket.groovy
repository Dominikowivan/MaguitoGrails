package maguitograils

class MonsterMarket {

    String name
    List<MonsterCostAssociation> monstersCatalogue = []
    //Opte por una clase que maneje la asociacion tras haber puteado mucho
    //queriendo usar Map (No se pueden persistir, por lo menos facil [si es que se puede])

    static constraints = {
        name                nullable: false, lank:false, maxSize:255, unique:true
        monstersCatalogue   nullable: false
    }
}
