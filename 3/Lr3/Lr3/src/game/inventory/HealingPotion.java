package game.inventory;

import game.droids.Droid;

public class HealingPotion extends Item {
    private int healAmount;
    public HealingPotion(int healAmount) {
        super("Healing Potion");
        this.healAmount = healAmount;
    }
    @Override
    public void use(Droid user, Droid target) {
        user.takeDamage(-healAmount);
        System.out.println(user.getName() + " випив зілля і відновив " + healAmount + " HP!");
    }
}