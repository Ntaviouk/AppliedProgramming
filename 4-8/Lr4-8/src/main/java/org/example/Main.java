package org.example;

import model.*;
import service.*;
import command.*;
import java.util.*;
import java.io.*;

public class Main {
    private static Album album;
    private static AlbumService service;
    private static Playlist playlist;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<Integer, Command> menu = new LinkedHashMap<>();

        // 1. Створення нового альбому
        menu.put(1, new Command() {
            public boolean execute() {
                System.out.print("Enter album name: ");
                String name = sc.nextLine();
                album = new Album(name);
                service = new AlbumService(album);
                System.out.println("Album '" + name + "' created.");
                return true;
            }
            public String getDescription() { return "Create new album"; }
        });

        // 2. Додавання треку до альбому
        menu.put(2, new Command() {
            public boolean execute() {
                if (album == null) { System.out.println("No album created."); return true; }
                System.out.print("Enter track title: ");
                String title = sc.nextLine();
                System.out.print("Enter artist name: ");
                String artistName = sc.nextLine();
                System.out.print("Enter artist country: ");
                String artistCountry = sc.nextLine();
                System.out.print("Enter genre (ROCK, POP, JAZZ, CLASSICAL, ELECTRONIC, HIPHOP, OTHER): ");
                Genre genre = Genre.valueOf(sc.nextLine().toUpperCase());
                System.out.print("Enter duration (seconds): ");
                int dur = Integer.parseInt(sc.nextLine());
                Artist artist = new Artist(artistName, artistCountry);
                album.addTrack(new Track(title, artist, genre, dur));
                System.out.println("Track added.");
                return true;
            }
            public String getDescription() { return "Add track to album"; }
        });

        // 3. Створення плейліста
        menu.put(3, new Command() {
            public boolean execute() {
                System.out.print("Enter playlist name: ");
                String name = sc.nextLine();
                playlist = new Playlist(name);
                System.out.println("Playlist '" + name + "' created.");
                return true;
            }
            public String getDescription() { return "Create playlist"; }
        });

        // 4. Додавання треку з альбому до плейліста
        menu.put(4, new Command() {
            public boolean execute() {
                if (album == null || playlist == null) { System.out.println("Album or playlist not created."); return true; }
                System.out.println("Select track to add to playlist:");
                List<Track> tracks = album.getTracks();
                for (int i = 0; i < tracks.size(); i++) System.out.println(i + ": " + tracks.get(i));
                System.out.print("Enter track number: ");
                int idx = Integer.parseInt(sc.nextLine());
                if (idx >= 0 && idx < tracks.size()) {
                    playlist.addTrack(tracks.get(idx));
                    System.out.println("Track added to playlist.");
                } else System.out.println("Invalid index.");
                return true;
            }
            public String getDescription() { return "Add track from album to playlist"; }
        });

        // 5. Показ альбому
        menu.put(5, new Command() {
            public boolean execute() {
                if (service == null) { System.out.println("No album created."); return true; }
                ShowAlbumCommand cmd = new ShowAlbumCommand(service);
                return cmd.execute();
            }
            public String getDescription() { return "Show album"; }
        });

        // 6. Сортування альбому за жанром
        menu.put(6, new Command() {
            public boolean execute() {
                if (service == null) { System.out.println("No album created."); return true; }
                SortByGenreCommand cmd = new SortByGenreCommand(service);
                return cmd.execute();
            }
            public String getDescription() { return "Sort album by genre"; }
        });

        // 7. Пошук треків за діапазоном тривалості
        menu.put(7, new Command() {
            public boolean execute() {
                if (service == null) { System.out.println("No album created."); return true; }
                System.out.print("Enter minimum duration (sec): ");
                int min = Integer.parseInt(sc.nextLine());
                System.out.print("Enter maximum duration (sec): ");
                int max = Integer.parseInt(sc.nextLine());
                FindByDurationCommand cmd = new FindByDurationCommand(service, min, max);
                return cmd.execute();
            }
            public String getDescription() { return "Find tracks by duration"; }
        });

        // 8. Збереження альбому у файл
        menu.put(8, new Command() {
            public boolean execute() {
                if (album == null) { System.out.println("No album created."); return true; }
                System.out.print("Enter file name to save album: ");
                String fileName = sc.nextLine();
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
                    oos.writeObject(album);
                    System.out.println("Album saved to file: " + fileName);
                } catch (Exception e) {
                    System.out.println("Error saving album: " + e.getMessage());
                }
                return true;
            }
            public String getDescription() { return "Save album to file"; }
        });

        // 10. Завантаження альбому з файлу
        menu.put(10, new Command() {
            public boolean execute() {
                System.out.print("Enter file name to load album: ");
                String fileName = sc.nextLine();
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
                    album = (Album) ois.readObject();
                    service = new AlbumService(album);
                    System.out.println("Album loaded from file: " + fileName);
                } catch (Exception e) {
                    System.out.println("Error loading album: " + e.getMessage());
                }
                return true;
            }
            public String getDescription() { return "Load album from file"; }
        });

        // 9. Довідка
        menu.put(9, new HelpCommand(menu));
        // 0. Вихід
        menu.put(0, new ExitCommand());

        boolean running = true;
        while (running) {
            System.out.println("\n===== Menu =====");
            menu.forEach((k,v) -> System.out.println(k+" - "+v.getDescription()));
            System.out.print("Select option: ");
            String line = sc.nextLine();
            try {
                int choice = Integer.parseInt(line);
                Command c = menu.get(choice);
                if (c != null) running = c.execute();
                else System.out.println("Unknown choice");
            } catch(Exception e) {
                System.out.println("Invalid input. Enter a number.");
            }
        }
    }
}
