public class Position {
    public int x, y;
    public boolean isHorizontal = true;
    
    public Position(int x, int y, boolean isHorizontal) throws IllegalArgumentException {
        this.x = x;
        this.y = y;
        this.isHorizontal = isHorizontal;
    }
    
}
