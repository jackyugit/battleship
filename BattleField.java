import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class BattleField {
    private Ship[][] field = new Ship[10][10];
    List<Position> enemyHits = new ArrayList<Position>();
    
    List<Ship> hits = new ArrayList<Ship>();
    
    public enum Ship {
        Carrier(5),
        Battleship(4),
        Submarine(3),
        Cruiser(3),
        Patrolboat(2);
        public int size = 0;
        Ship(int size) {
            this.size = size;
        }
        public int getSize() {
            return this.size;
        }
    }
    
    private List<Position> positions = new ArrayList<Position>(5);
    
    public static BattleField RandomeComputerBoard() {
        Random random = new Random();
        BattleField bf = new BattleField();
        Ship[] ships = Ship.values();
        for (int i=0;i<ships.length;i++) {
            Position position = new Position(random.nextInt(10), random.nextInt(10), random.nextBoolean());
            try {
                bf.placeShip(ships[i], position);
            } catch (IllegalArgumentException exception) {
                i = i-1;
            }
        }
        return bf;
    }
    
    public BattleField () { 
    }
    
    public void printHits() {
        for (Ship ship: hits) {
            System.out.println("    "+ship+" has been hit");
        }
    }
    
    public void printPlacements() {
        for (Position position: positions) {
            System.out.print("    "+field[position.x][position.y] +
                    " is placed at ("+position.x+","+position.y+") to ");
            if (position.isHorizontal) {
                System.out.println("("+(position.x+field[position.x][position.y].size-1)+","+position.y+")");
            } else {
                System.out.println("("+position.x+","+(position.y+field[position.x][position.y].size-1)+")");
            }
        }
    }
    
    public boolean gameOver() {
        return hits.size() == 5;
    }
    
    public boolean hasBeenPlanted(Position position) {
        return enemyHits.contains(position);
    }
    
    public Ship hit(Position position) {
        if (field[position.x][position.y] != null) {
            if (!hits.contains(field[position.x][position.y])) {
                hits.add(field[position.x][position.y]);
            }
        }
        enemyHits.add(position);
        return field[position.x][position.y];
    }
    
    public void placeShip(Ship ship, Position position) throws IllegalArgumentException {
        if (position.isHorizontal) {
            if (position.x + ship.size >= 10) {
                throw new IllegalArgumentException("Cannot fit "+ship+" onto "+position);
            }
            for (int i=0;i<ship.size;i++) {
                if (field[position.x+i][position.y] != null) {
                    throw new IllegalArgumentException("Position "+position+" is occupied by "+field[position.x+i][position.y]);
                }
            }
            for (int i=0;i<ship.size;i++) {
                field[position.x+i][position.y] = ship;
            }
        } else {
            if (position.y + ship.size >= 10) {
                throw new IllegalArgumentException("Cannot fit "+ship+" onto ("+position.x+","+position.y+")");
            }
            for (int i=0;i<ship.size;i++) {
                if (field[position.x][position.y+i] != null) {
                    throw new IllegalArgumentException("Position ("+position.x+","+(position.y+i)+") is occupied by "+field[position.x+i][position.y]);
                }
            }
            for (int i=0;i<ship.size;i++) {
                field[position.x][position.y+i] = ship;
            }
        }
        positions.add(position);
    }
    
}
