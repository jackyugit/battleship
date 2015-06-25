

import java.io.Console;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting the Battleship");
        System.out.println("Place your boats, using 09 format to indicate (xy) ends with - to indicate horizontal.");
        BattleField userField = new BattleField();
        Console c = System.console();
        BattleField.Ship[] ships = BattleField.Ship.values();
        for (int i=0;i<ships.length;i++) {
            String position = c.readLine("Place "+ships[i]+" with size "+ships[i].size+" ==> ");
            try {
                Position placement = getPlacement(position);
                try {
                    userField.placeShip(ships[i], placement);
                } catch (IllegalArgumentException exception) {
                    System.out.println("!!"+exception.getMessage());
                    i--;
                }
            } catch (Throwable exception) {
                System.out.println(position+" is not a valid position");
                i--;
            }
            System.out.println("============Here is your placements=========");
            userField.printPlacements();
            System.out.println("============================================");
        }
        BattleField computerField = BattleField.RandomeComputerBoard();
        System.out.println("============Here is computer's placements=====");
        computerField.printPlacements();
        System.out.println("=============================================");
        
        // at this point, game can start
        startGame(userField, computerField);
    }
    
    public static void startGame(BattleField user, BattleField computer) {
        Console console = System.console();
        Random random = new Random();
        while (!user.gameOver() && !computer.gameOver()) {
            while (true) {
                String position = console.readLine("Plant your bomb=>");
                try {
                    Position bomb = getPosition(position);
                    BattleField.Ship cship = computer.hit(bomb);
                    if (cship == null) {
                        System.out.println("  Sorry, you missed...");
                    } else {
                        System.out.println("  Wow, you hit "+cship);
                    }
                    break;
                } catch (Throwable e) {
                    System.out.println("!!"+position+" not a valid position. Try again");
                }
            }
            // now, it is computer's turn
            Position computerBomb = new Position(random.nextInt(10), random.nextInt(10), false);
            BattleField.Ship uship = user.hit(computerBomb);
            if (uship == null) {
                System.out.println("  Lucky! Computer missed at ("+computerBomb.x+","+computerBomb.y+")");
            } else {
                System.out.println("  Whoops! Computer hit your "+uship+" at ("+computerBomb.x+","+computerBomb.y+")");
            }
        }
    }
    
    public static Position getPlacement(String input) {
        return new Position(Integer.parseInt(input.charAt(0)+""), Integer.parseInt(""+input.charAt(1)), '-' == input.charAt(2));
    }
    
    public static Position getPosition(String input) {
        return new Position(Integer.parseInt(input.charAt(0)+""), Integer.parseInt(""+input.charAt(1)), false);
    }
}
