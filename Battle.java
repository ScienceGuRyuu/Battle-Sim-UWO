public class Battle {
    static PC hero = new PC("Joe", 25, 10);
    static Enemy monster = new Enemy("Ghost", 18, 8);
    
    static boolean isBattleHappening(int PCHP, int enemyHP){
        return PCHP > 0 && enemyHP > 0;
    }
    
    static void handlePCTurn(){
        PC.PCAttack heroAttackSelection = hero.attackSelection();
        int heroAccuracy = hero.attemptAttack(heroAttackSelection);
        boolean didHeroHitAttack = monster.hitByAttack(heroAccuracy);
        int heroDamageDealt = hero.dealDamage(didHeroHitAttack, heroAttackSelection.ordinal());
        monster.takeDamage(heroDamageDealt);
        System.out.println("You dealt " + heroDamageDealt + " damage to the monster!");
        if (monster.currentHP <=0){
            System.out.println("Wow, you actually won!");
            System.exit(0);
        } else {
            System.out.println("The monster has " + monster.currentHP + " HP remaining.");
        }
    }

    static void handleEnemyTurn(){
        Enemy.EnemyAttack monsterAttackSelection = monster.attackSelection();
        boolean didMonsterHitAttack = hero.didMonsterHitPC();
        int monsterDamageDealt = monster.dealDamage(didMonsterHitAttack, monsterAttackSelection);
        hero.takeDamage(monsterDamageDealt);
        System.out.println("\nThe monster dealt " + monsterDamageDealt + " damage to you!");
        if (hero.currentHP <= 0){
            System.out.println("Congratulations! Your lifeless corpse will almost be remembered...");
            System.exit(0);
        } else {
            System.out.println("You have " + hero.currentHP + " HP remaining.\n");
        }
    }

    private enum AttackTurn{
        PC, ENEMY
    }

    public static void main(String[] args) {
        AttackTurn turn = (hero.dexterity > monster.dexterity) ? AttackTurn.PC : AttackTurn.ENEMY;
        while (isBattleHappening(hero.currentHP, monster.currentHP)){
            switch (turn) {
                case PC -> {
                    handlePCTurn();
                    turn = AttackTurn.ENEMY;
                }
                case ENEMY -> {
                    handleEnemyTurn();
                    turn = AttackTurn.PC;
                }
            }
        }
    }
}
