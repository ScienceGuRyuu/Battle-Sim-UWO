import java.util.stream.IntStream;

/*
    Enemies need to give exp based on how hard they are.
    TODO implement damage logic, experience given, and a potential level mechanic?
 */
public class Enemy extends Combatant {
    // weight X dx X number of rolls
    int[][] attacks = new int[][] { {60, 30, 10}, {2, 15, 5}, {3, 1, 1} };

    public Enemy(String name, int startingHP, int dexterity) {
        super(name, startingHP, dexterity);
    }


    public int attackSelection(){
        Dice roller = new Dice(100);
        int[] attackSelectionRoll = roller.rollDie(1);
        if (attackSelectionRoll[0] < 60){
            return 0;
        } else if (attackSelectionRoll[0] < 90){
            return 1;
        } else {
            return 2;
        }

    }

    boolean hitByAttack (int opponentDexterity){
        return opponentDexterity > this.dexterity;
    }

    @Override
    int dealDamage(boolean wasHit, int selectedAttack) {
        if (selectedAttack == 2){
            this.heal(2);
            return 0;
        }
        Dice roller = new Dice(this.attacks[1][selectedAttack]);
        int[] rolls = roller.rollDie(this.attacks[2][selectedAttack]);
        return IntStream.of(rolls).sum();
    }

    @Override
    void heal(int selectedAttack) {
        Dice roller = new Dice(this.attacks[1][selectedAttack]);
        int[] rolls = roller.rollDie(this.attacks[2][selectedAttack]);
        int damage = IntStream.of(rolls).sum();
        if (this.currentHP == this.maxHP){
            System.out.println(this.combatantName + "looks at you menacingly...");
        } else if (this.currentHP + damage > this.maxHP){
            damage = this.maxHP - this.currentHP;
            System.out.println(this.combatantName + " healed " + damage + " HP.");
            this.currentHP = this.maxHP;
        } else {
            System.out.println(this.combatantName + " healed " + damage + " HP.");
        }
    }
}
