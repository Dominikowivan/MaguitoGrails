package maguitograils

class Elite extends Monster{

    static constraints = {
    }

    @Override
    def isWeary(){ (maxLife / 4) >= actualLife}

}
