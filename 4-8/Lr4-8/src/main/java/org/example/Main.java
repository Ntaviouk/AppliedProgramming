package org.example;

import model.*;
import service.AlbumService;

import java.util.*;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Album album = new Album("My Favorite Tracks");
        AlbumService service = new AlbumService(album);

        // ==== Меню команд ====
        Map<Integer, MenuCommand> menu = new LinkedHashMap<>();

        menu.put(0, new MenuCommand("Exit", () -> {
            System.out.println("Exiting...");
            System.exit(0);
        }));

        menu.put(1, new MenuCommand("Create new album", () -> {
            System.out.print("Enter album name: ");
            scanner.nextLine(); // очищення після nextInt
            String name = scanner.nextLine();
            service.getAlbum().setName(name);
            System.out.println("Album created: " + name);
        }));

        menu.put(2, new MenuCommand("Add track manually", () -> {
            scanner.nextLine(); // очищення
            System.out.print("Enter track title: ");
            String title = scanner.nextLine();

            System.out.print("Enter artist name: ");
            String artistName = scanner.nextLine();

            System.out.print("Enter artist country: ");
            String country = scanner.nextLine();

            System.out.print("Enter genre (ROCK, POP, JAZZ, etc.): ");
            String genreName = scanner.nextLine().toUpperCase();
            Genre genre;
            try {
                genre = Genre.valueOf(genreName);
            } catch (IllegalArgumentException e) {
                genre = Genre.OTHER;
            }

            System.out.print("Enter duration (seconds): ");
            int duration = scanner.nextInt();

            Artist artist = new Artist(artistName, country);
            Track track = new Track(title, artist, genre, duration);
            service.getAlbum().addTrack(track);
            System.out.println("✅ Track added: " + track);
        }));

        menu.put(3, new MenuCommand("Load album from file", () -> {
            scanner.nextLine();
            System.out.print("Enter file path: ");
            String path = scanner.nextLine();
            try {
                service.loadFromFile(path);
                System.out.println("✅ Album loaded successfully!");
            } catch (Exception e) {
                System.out.println("❌ Error loading album: " + e.getMessage());
            }
        }));

        menu.put(4, new MenuCommand("Show album", service::printAlbum));

        menu.put(5, new MenuCommand("Sort tracks by genre", () -> {
            service.sortByGenre();
            System.out.println("✅ Sorted by genre.");
        }));

        menu.put(6, new MenuCommand("Find tracks by duration range", () -> {
            System.out.print("Enter minimum duration (seconds): ");
            int min = scanner.nextInt();
            System.out.print("Enter maximum duration (seconds): ");
            int max = scanner.nextInt();
            var found = service.findByDurationRange(min, max);
            if (found.isEmpty()) {
                System.out.println("No tracks found in this range.");
            } else {
                System.out.println("Tracks found:");
                found.forEach(System.out::println);
            }
        }));

        menu.put(7, new MenuCommand("Show total album duration", () -> {
            int total = service.getAlbum().totalDuration();
            System.out.printf("Total duration: %d:%02d%n", total / 60, total % 60);
        }));

        menu.put(8, new MenuCommand("Help", () -> {
            System.out.println("This program allows managing a music album:");
            System.out.println(" - Add tracks manually or load from file");
            System.out.println(" - Sort tracks by genre");
            System.out.println(" - Find tracks by duration range");
            System.out.println(" - Display total album info");
        }));

        // ==== Головний цикл ====
        while (true) {
            System.out.println("\n===== MENU =====");
            menu.forEach((key, cmd) -> System.out.println(key + " - " + cmd.getName()));
            System.out.print("Select option: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input, enter a number.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            MenuCommand cmd = menu.get(choice);
            if (cmd != null) {
                try {
                    cmd.getAction().run();
                } catch (Exception e) {
                    System.out.println("❌ Error: " + e.getMessage());
                }
            } else {
                System.out.println("No such command.");
            }
        }
    }
}


class MenuCommand {
    private final String name;
    private final Runnable action;

    public MenuCommand(String name, Runnable action) {
        this.name = name;
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public Runnable getAction() {
        return action;
    }
}
