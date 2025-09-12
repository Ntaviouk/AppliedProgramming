package game;

import game.droids.*;
import game.battle.Battle;
import game.battle.TeamBattle;
import game.utils.FileManager;
import game.inventory.*;
import java.util.*;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final List<Droid> droids = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n__ МЕНЮ __");
            System.out.println("1. Створити дроїда");
            System.out.println("2. Показати список дроїдів");
            System.out.println("3. Запустити бій 1 на 1");
            System.out.println("4. Запустити бій команда на команду");
            System.out.println("5. Відтворити бій з файлу");
            System.out.println("6. Вийти");
            System.out.print("Ваш вибір: ");

            switch (sc.nextInt()) {
                case 1 -> createDroid();
                case 2 -> showDroids();
                case 3 -> battle1v1();
                case 4 -> teamBattle();
                case 5 -> FileManager.replayBattle("battle.txt");
                case 6 -> { return; }
            }
        }
    }

    private static void createDroid() {
        System.out.println("Оберіть тип дроїда: 1-Танк, 2-Атакуючий, 3-Розвідник");
        int type = sc.nextInt();
        System.out.print("Введіть ім'я: ");
        String name = sc.next();

        Droid d = switch (type) {
            case 1 -> new TankDroid(name);
            case 2 -> new AttackDroid(name);
            case 3 -> new ScoutDroid(name);
            default -> null;
        };
        if (d != null) {
            d.addItem(new HealingPotion(50));
            d.addItem(new DamageBoost(20));
            droids.add(d);
            System.out.println("Створено: " + d);
        }
    }

    private static void showDroids() {
        if (droids.isEmpty()) {
            System.out.println("Немає створених дроїдів!");
        } else {
            for (int i = 0; i < droids.size(); i++) {
                System.out.println((i+1) + ". " + droids.get(i));
            }
        }
    }

    private static void battle1v1() {
        showDroids();
        if (droids.size() < 2) {
            System.out.println("Недостатньо дроїдів для бою!");
            return;
        }
        System.out.print("Оберіть дроїда 1: ");
        Droid d1 = droids.get(sc.nextInt()-1);
        System.out.print("Оберіть дроїда 2: ");
        Droid d2 = droids.get(sc.nextInt()-1);

        Battle b = new Battle(d1, d2);
        String log = b.start();
        System.out.println(log);

        FileManager.saveBattle("battle.txt", log);
    }

    private static void teamBattle() {
        showDroids();
        if (droids.size() < 4) {
            System.out.println("Недостатньо дроїдів для командного бою! (мінімум 4)");
            return;
        }

        System.out.print("Скільки дроїдів у команді? ");
        int teamSize = sc.nextInt();

        List<Droid> team1 = new ArrayList<>();
        List<Droid> team2 = new ArrayList<>();

        System.out.println("=== Формування Team1 ===");
        for (int i = 0; i < teamSize; i++) {
            System.out.print("Оберіть дроїда для Team1: ");
            team1.add(droids.get(sc.nextInt()-1));
        }

        System.out.println("=== Формування Team2 ===");
        for (int i = 0; i < teamSize; i++) {
            System.out.print("Оберіть дроїда для Team2: ");
            team2.add(droids.get(sc.nextInt()-1));
        }

        TeamBattle tb = new TeamBattle(team1, team2);
        String log = tb.start();
        System.out.println(log);

        FileManager.saveBattle("team_battle.txt", log);
    }
}
