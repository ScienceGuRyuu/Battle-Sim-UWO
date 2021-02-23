/*
    A class that will be able to handle rolling dice with variable number of sides and rolls.
 */
import java.util.*;

public class Dice {
    int dx;
    Random randomRoll = new Random();

    public Dice(int sides){
        this.dx = sides;
    }

    public int[] rollDie(int numberOfRolls){
        int currentRollIndex = 0;
        int[] listOfRolls = new int[numberOfRolls];
        while (currentRollIndex <= numberOfRolls - 1) {
            int roll = randomRoll.nextInt(this.dx - 1);
            listOfRolls[currentRollIndex] = roll + 1;
            currentRollIndex++;
        }
        return listOfRolls;
    }
}
