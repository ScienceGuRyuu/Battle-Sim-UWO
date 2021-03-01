import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * Beyond the basics, a PC needs to gain dexterity and to know if the monster attack landed.
 */

public class PC extends Combatant{
    // accuracy X dx X number of rolls
    private int[][] attacks = new int[][] { {10, 5, 1, 100, 100}, {4, 12, 20, 9, 5}, {2, 1, 1, 1, 1} };
    private int dexterityBonus;

    public PC(String name, int startingHP, int dexterity) {
        super(name, startingHP, dexterity);
    }

    public enum PCAttack {
        PUNCH, SLASH, WAND, HEAL, MANEUVER, NONE
    }

    private void gainDexterity(int selectedAttack){
        int[] rolls = Dice.rollDice(this.attacks[2][selectedAttack], this.attacks[1][selectedAttack]);
        int increase = IntStream.of(rolls).sum();
        this.dexterityBonus += increase;
        System.out.println("Your observation has gained you a +" + increase + " temporary dexterity bonus.");
    }

    public void heal(int selectedAttack){
        int[] rolls = Dice.rollDice(this.attacks[2][selectedAttack], this.attacks[1][selectedAttack]);
        int damage = IntStream.of(rolls).sum();
        if (this.currentHP == this.maxHP){
            System.out.println("Everyone's vitality has an upper limit you know...");
        } else if (this.currentHP + damage > this.maxHP){
            damage = this.maxHP - this.currentHP;
            System.out.println("You healed " + damage + " HP.");
            this.currentHP = this.maxHP;
        } else {
            System.out.println("You healed " + damage + " HP.");
            this.currentHP = this.currentHP + damage;
        }
    }

    protected int dealDamage(boolean wasAHit, int selectedAttack) {
        int[] rolls = Dice.rollDice(this.attacks[2][selectedAttack], this.attacks[1][selectedAttack]);
        int damage = IntStream.of(rolls).sum();
        if (selectedAttack == 3) {
            this.heal(selectedAttack);
            return 0;
        } else if (selectedAttack == 4){
            this.gainDexterity(selectedAttack);
            return 0;
        } else if(wasAHit){
            System.out.println("Take that foul beast!");
            return damage;
        } else {
            System.out.println("Opps...!");
            return 0;
        }
    }

    protected int attemptAttack(PCAttack selectedAttack) {
        int accuracyBoost = this.attacks[0][selectedAttack.ordinal()];
        return accuracyBoost + this.dexterity;
    }

    protected PCAttack attackSelection(){
        boolean validInput = false;
        Scanner input = new Scanner(System.in);
        PCAttack choice = PCAttack.NONE;
        int PCChoice;
        while (!validInput){
            System.out.println("Please pick an attack: \n" +
                               "\t 1.) Punch of Justice:\n" +
                               "\t\t A highly accurate, consistent, and boring attack.\n" +
                               "\t 2.) Slash of Destiny:\n" +
                               "\t\t A Higher risk attack that comes with more power.\n" +
                               "\t 3.) Mysterious Wand:\n" +
                               "\t\t You do not remember how you got this. Expect inconstant damage output\n" +
                               "\t 4.) Magical Healing:\n" +
                               "\t\t Heal yourself anywhere from 1 to 9 HP.\n" +
                               "\t 5.) Strategic Maneuvering:\n" +
                               "\t\t Lessen your odds of being hit next turn.");
            System.out.print("\n\nSelect 1, 2, 3, 4, or 5: ");
            try {
                PCChoice = Integer.parseInt(input.nextLine());
                if (PCChoice > 5 || PCChoice <= 0){
                    throw new ArithmeticException("Please enter a number between 1 and 5...\n");
                }
                validInput = true;
                switch (PCChoice) {
                    case 1 -> choice = PCAttack.PUNCH;
                    case 2 -> choice = PCAttack.SLASH;
                    case 3 -> choice = PCAttack.WAND;
                    case 4 -> choice = PCAttack.HEAL;
                    case 5 -> choice = PCAttack.MANEUVER;
                }
            } catch (NumberFormatException | ArithmeticException exception) {
                System.out.println("Please enter a number from 1 to 5...\n");
            }
        }
            System.out.println("\n");
        return choice;
    }

    public boolean didMonsterHitPC(){
        int[] monsterHitRoll = Dice.rollDice(1, 50);
        boolean PCWasHit = monsterHitRoll[0] <= (45 + dexterityBonus);
        this.dexterityBonus = 0;
        return PCWasHit;
    }
}


