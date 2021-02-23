import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.IntStream;

/*
    PC needs to have items to use as well as a way to gain exp and to change their stats and levels.
    TODO implement damage logic, experience gains, stat changes, and other things as needed
 */
public class PC extends Combatant{
    // accuracy X dx X number of rolls
    int[][] attacks = new int[][] { {10, 5, 1, 100, 100}, {4, 12, 20, 9, 5}, {2, 1, 1, 1, 1} };
    int tempDexterity;
    int firstTurn = 0;

    public PC(String name, int startingHP, int dexterity) {
        super(name, startingHP, dexterity);
    }


    void gainDexterity(int selectedAttack){
        Dice roller = new Dice(this.attacks[1][selectedAttack]);
        int[] rolls = roller.rollDie(this.attacks[2][selectedAttack]);
        int increase = IntStream.of(rolls).sum();
        this.tempDexterity += increase;
        System.out.println("Your observation has gained you a +" + increase + " temporary dexterity bonus.");
    }

    @Override
    public void heal(int selectedAttack){
        Dice roller = new Dice(this.attacks[1][selectedAttack]);
        int[] rolls = roller.rollDie(this.attacks[2][selectedAttack]);
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

    @Override
    int dealDamage(boolean wasAHit, int selectedAttack) {
        Dice roller = new Dice(this.attacks[1][selectedAttack]);
        int[] rolls = roller.rollDie(this.attacks[2][selectedAttack]);
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

    int attemptAttack(int selectedAttack) {
        int accuracyBoost = this.attacks[1][selectedAttack];
        return accuracyBoost + this.dexterity;
    }

    int attackSelection(){
        boolean valid_input = false;
        Scanner input = new Scanner(System.in);
        int PCChoice = -1;

        while (!valid_input){
            System.out.println("""
                        Please pick an attack:\s
                        \t 1.) Punch of Justice:
                        \t\t A highly accurate, consitant, and boring attack.
                        \t 2.) Slash of Destiny:
                        \t\t A Higher risk attack that comes with more power.
                        \t 3.) Mysterious Wand:
                        \t\t You do not remember how you got this. Expect inconstant damage output
                        \t 4.) Magical Healing:
                        \t\t Heal yourself anywhere from 1 to 9 HP.
                        \t 5.) Strategic Maneuvering:
                        \t\t Lessen your odds of being hit next turn.""");
            System.out.print("\n\nSelect 1, 2, 3, 4, or 5: ");
            try {
                PCChoice = input.nextInt();
                if (PCChoice > 5){
                    throw new InputMismatchException();
                }
                valid_input = true;
            } catch (InputMismatchException exception) {
                System.out.println("Please enter a number from 1 to 5...");
            }
        }
            System.out.println("\n");
            return PCChoice - 1;
    }

    public boolean didMonsterHitPC(){
        Dice roller = new Dice(50);
        int[] monsterHitRoll = roller.rollDie(1);
        boolean PCWasHit = monsterHitRoll[0] >= (45 + tempDexterity);
        this.tempDexterity = this.dexterity;
        return PCWasHit;
    }
}


