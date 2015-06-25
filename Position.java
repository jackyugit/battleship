public class Position {
    public int x, y;
    public boolean isHorizontal = true;
    
    public Position(int x, int y, boolean isHorizontal) throws IllegalArgumentException {
        this.x = x;
        this.y = y;
        this.isHorizontal = isHorizontal;
    }
    
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof Position) {
            Position position = (Position)obj;
            return (position.x == this.x && position.y == this.y);
        }
        return false;
    }
    
    public String toString() {
        return "("+x+","+y+")";
    }
}
