import java.util.*;
import java.util.stream.IntStream;

/**
 * A class that will be able to handle rolling dice with variable number of sides and rolls as well as sum the rolls.
 *
 */

public class Dice {
    private int dx;
    Random randomRoll;

    public Dice(int sides){
        randomRoll = new Random();
        setDx(sides);
    }
    /**
     * Currently unneeded constructor, left in for potential expansion of project into a more fleshed-out game.
     */
    public void setDx(int sides){
        this.dx = sides;
    }

    /**
     * Currently unused, left in for potential expansion of project into a more fleshed-out game.
     */
    public int[] rollDice(int numberOfRolls){
        int[] listOfRolls = new int[numberOfRolls];
        for(int i=1; i < numberOfRolls; i++){
            int roll = randomRoll.nextInt(this.dx);
            listOfRolls[i-1] = roll + 1;
        }
        return listOfRolls;
    }

    /**
     * Currently unused, left in for potential expansion of project into a more fleshed-out game.
     */
    public int sumOfDiceRoll (int[] rolls){
        return IntStream.of(rolls).sum();
    }

    /**
     * Useful for cased wen a method only requires one roll procedure
     */
    public static int[] rollDice(int numberOfRolls, int dx){
        int[] listOfRolls = new int[numberOfRolls];
        for(int i=0; i < numberOfRolls; i++){
            int roll = (int)(Math.random() * (dx - 1) + 1);
            listOfRolls[i] = roll;
        }
        return listOfRolls;
    }

}
