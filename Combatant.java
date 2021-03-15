/**
 * The skeleton of what the enemy and player (PC) will need.
 * They will need to have health points (HP), a name, and dexterity along with a way to manage HP.
 */

abstract class Combatant {
    protected int maxHP;
    protected int currentHP;
    protected String combatantName;
    protected int dexterity;

    public Combatant(String name, int startingHP, int dexterity) {
        this.maxHP = startingHP;
        this.currentHP = startingHP;
        this.combatantName = name;
        this.dexterity = dexterity;
    }

    void takeDamage(int damageToTake){
        this.currentHP = this.currentHP - damageToTake;
    }
}




