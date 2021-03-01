import java.util.stream.IntStream;

/**
 * Beyond the basics, an Enemy needs to know if an attack from PC has landed and to have a move pool.
 */

public class Enemy extends Combatant {
    // Weight X dx X number of rolls
    private int[][] attacks = new int[][] { {60, 30, 10}, {2, 15, 5}, {3, 1, 1} };

    public enum EnemyAttack {
        WEAK, STRONG, HEAL
    }

    public Enemy(String name, int startingHP, int dexterity) {
        super(name, startingHP, dexterity);
    }

    public boolean hitByAttack (int opponentDexterity){
        return opponentDexterity > this.dexterity;
    }

    public EnemyAttack attackSelection(){
        int[] attackSelectionRoll = Dice.rollDice(1, 100);
        EnemyAttack choice;
        if (attackSelectionRoll[0] < attacks[0][0]){
            choice = EnemyAttack.WEAK;
        } else if (attackSelectionRoll[0] < attacks[0][0] + attacks[0][1]) {
            choice = EnemyAttack.STRONG;
        } else {
            choice = EnemyAttack.HEAL;
        }
        return choice;
    }

    public int dealDamage(boolean wasHit, EnemyAttack selectedAttack) {
        if (wasHit) {
            if (selectedAttack == EnemyAttack.HEAL) {
                this.heal(selectedAttack.ordinal());
                return 0;
            }
            int[] rolls = Dice.rollDice(this.attacks[2][selectedAttack.ordinal()], this.attacks[1][selectedAttack.ordinal()]);
            return IntStream.of(rolls).sum();
        } else {
            System.out.println('\n' + this.combatantName + " missed!");
            return 0;
        }
    }

    public void heal(int selectedAttack) {
        int[] rolls = Dice.rollDice(this.attacks[2][selectedAttack], this.attacks[1][selectedAttack]);
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
