package game.utils;

import java.io.*;

public class FileManager {
    public static void saveBattle(String filename, String log) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(log);
            System.out.println("Бій збережено у файл: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void replayBattle(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            System.out.println("=== Відтворення бою з файлу ===");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
