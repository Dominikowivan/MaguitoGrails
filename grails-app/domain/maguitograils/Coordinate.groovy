package maguitograils

class Coordinate {
    Integer x
    Integer y



    @Override
    boolean equals(def o) {

        o.x == x && o.y == y
    }




    static constraints = {
    }
}
