package utils;

public  class Coordinate {
    public int x, y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public static Coordinate of(int x, int y) {
        return new Coordinate(x, y);
    }

    public String toString(){
        return String.format("(%s,%s)", this.x, this.y);
    }

    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        final Coordinate other = (Coordinate) obj;
        return this.x == other.x && this.y == other.y;

    }

}
