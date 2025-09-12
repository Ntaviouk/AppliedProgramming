package game.inventory;

import game.droids.Droid;

public class DamageBoost extends Item {
    private int bonusDamage;
    public DamageBoost(int bonusDamage) {
        super("Damage Boost");
        this.bonusDamage = bonusDamage;
    }
    @Override
    public void use(Droid user, Droid target) {
        int dmg = user.getDamage() + bonusDamage;
        target.takeDamage(dmg);
        System.out.println(user.getName() + " використав Damage Boost і наніс " + dmg + " шкоди!");
    }
}