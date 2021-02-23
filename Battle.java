public class Battle {
    static boolean doesPCMoveFirst (int PCDexterity, int EnemyDexterity){
        return PCDexterity > EnemyDexterity;
    }

    static boolean isBattleHappening(int PCHP, int enemyHP){
        if (PCHP > 0 && enemyHP >0){
            return true;
        }
        return PCHP <= 0 && enemyHP <= 0;
    }

    public static void main(String[] args) {
        PC hero = new PC("Joe", 25, 10);
        Enemy monster = new Enemy("Ghost", 18, 8);
        int attackTurn;
        if (hero.dexterity > monster.dexterity){
            attackTurn = 0;
        } else {
            attackTurn = 1;
        }
        while (isBattleHappening(hero.currentHP, monster.currentHP)){
            switch (attackTurn) {
                case 0 -> {
                    int heroAttackSelection = hero.attackSelection();
                    int heroAccuracy = hero.attemptAttack(heroAttackSelection);
                    boolean didHeroHitAttack = monster.hitByAttack(heroAccuracy);
                    int heroDamageDealt = hero.dealDamage(didHeroHitAttack, heroAttackSelection);
                    monster.takeDamage(heroDamageDealt);
                    System.out.println("You dealt " + heroDamageDealt + " damage to the monster!");
                    System.out.println("The monster has " + monster.currentHP + " HP remaining.");
                    attackTurn++;
                }
                case 1 -> {
                    int monsterAttackSelection = monster.attackSelection();
                    boolean didMonsterHitAttack = hero.didMonsterHitPC();
                    int monsterDamageDealt = monster.dealDamage(didMonsterHitAttack, monsterAttackSelection);
                    hero.takeDamage(monsterDamageDealt);
                    System.out.println("The monster dealt " + monsterDamageDealt + " damage to you!");
                    System.out.println("You have " + hero.currentHP + " HP remaining.");
                    attackTurn--;
                }
            }

        }
    }
}
