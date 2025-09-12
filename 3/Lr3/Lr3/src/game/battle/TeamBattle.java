package game.battle;

import game.droids.Droid;
import java.util.List;
import java.util.Scanner;

public class TeamBattle {
    private List<Droid> team1;
    private List<Droid> team2;
    private StringBuilder log = new StringBuilder();
    private Scanner sc = new Scanner(System.in);

    public TeamBattle(List<Droid> team1, List<Droid> team2) {
        this.team1 = team1;
        this.team2 = team2;
    }

    public String start() {
        log.append("Бій команд: Team1 VS Team2\n");

        while (isTeamAlive(team1) && isTeamAlive(team2)) {
            Droid attacker = getAliveDroid(team1);
            Droid defender = getAliveDroid(team2);
            turn(attacker, defender);

            if (!isTeamAlive(team2)) break;

            attacker = getAliveDroid(team2);
            defender = getAliveDroid(team1);
            turn(attacker, defender);
        }

        if (isTeamAlive(team1)) log.append("Team1 перемогла!\n");
        else log.append("Team2 перемогла!\n");

        return log.toString();
    }

    private void turn(Droid attacker, Droid defender) {
        if (attacker == null || defender == null) return;

        System.out.println("\nХід дроїда: " + attacker.getName());
        System.out.println("1 - Атакувати");
        System.out.println("2 - Використати предмет (якщо є)");
        System.out.print("Ваш вибір: ");
        int choice = sc.nextInt();

        if (choice == 2 && !attacker.getInventory().isEmpty()) {
            for (int i = 0; i < attacker.getInventory().size(); i++) {
                System.out.println((i+1) + ". " + attacker.getInventory().get(i));
            }
            System.out.print("Оберіть предмет: ");
            int itemIndex = sc.nextInt() - 1;
            attacker.useItem(itemIndex, defender);
            log.append(attacker.getName()).append(" використав предмет!\n");
        } else {
            defender.takeDamage(attacker.getDamage());
            log.append(attacker.getName()).append(" атакував ").append(defender.getName())
                    .append(" на ").append(attacker.getDamage())
                    .append(" (залишилось HP: ").append(defender.getHealth()).append(")\n");
        }
    }

    private boolean isTeamAlive(List<Droid> team) {
        return team.stream().anyMatch(Droid::isAlive);
    }

    private Droid getAliveDroid(List<Droid> team) {
        return team.stream().filter(Droid::isAlive).findAny().orElse(null);
    }
}
