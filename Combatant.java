/*
    This is the basic idea of the PC and enemies. Each will have basic stats and a set move-list to choose from.
    At minimum, both need to be able to deal and take damage.
 */
import java.io.*;
abstract class Combatant {
    public int maxHP;
    public int currentHP;
    public String combatantName;
    public int dexterity;

    public Combatant(String name, int startingHP, int dexterity) {
        this.maxHP = startingHP;
        this.currentHP = startingHP;
        this.combatantName = name;
        this.dexterity = dexterity;
    }

    abstract int dealDamage (boolean wasAHit, int selectedAttack);

    public void takeDamage (int damageTaken){
        this.currentHP = this.currentHP - damageTaken;
    }

    abstract void heal(int selectedAttack);
}




