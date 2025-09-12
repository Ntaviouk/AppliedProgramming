package game.battle;

import game.droids.Droid;
import java.util.Random;

public class Battle {
    private Droid d1;
    private Droid d2;
    private StringBuilder log = new StringBuilder();

    public Battle(Droid d1, Droid d2) {
        this.d1 = d1;
        this.d2 = d2;
    }

    public String start() {
        Random rand = new Random();
        log.append("Бій: ").append(d1.getName()).append(" VS ").append(d2.getName()).append("\n");

        while (d1.isAlive() && d2.isAlive()) {
            if (rand.nextBoolean()) attack(d1, d2);
            else attack(d2, d1);
        }

        if (d1.isAlive()) log.append(d1.getName()).append(" переміг!\n");
        else log.append(d2.getName()).append(" переміг!\n");

        return log.toString();
    }

    private void attack(Droid attacker, Droid defender) {
        defender.takeDamage(attacker.getDamage());
        log.append(attacker.getName()).append(" атакував ").append(defender.getName())
                .append(" на ").append(attacker.getDamage())
                .append(" (залишилось HP: ").append(defender.getHealth()).append(")\n");
    }
}