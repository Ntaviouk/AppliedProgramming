package org.example;

import model.*;
import service.AlbumService;
import util.AudioPlayer;
import util.AudioRecorder;
import util.MenuCommand;

import java.util.*;
import java.io.*;

public class Main {

    // Static fields for access within anonymous classes
    private static Album album;
    private static AlbumService service;
    private static Map<String, Playlist> playlists = new LinkedHashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Map<Integer, MenuCommand> menu = new LinkedHashMap<>();

        // Exit
        menu.put(0, new MenuCommand("Exit") {
            public void execute() {
                System.out.println("Exiting...");
                System.exit(0);
            }
        });

        // Create new album
        menu.put(1, new MenuCommand("Create new album") {
            public void execute() {
                scanner.nextLine();
                System.out.print("Enter album name: ");
                String name = scanner.nextLine();
                album = new Album(name);
                service = new AlbumService(album);
                System.out.println("✅ Album created: " + name);
            }
        });

        // Add track
        menu.put(2, new MenuCommand("Add track to album") {
            public void execute() {
                if (album == null) {
                    System.out.println("❌ Create an album first!");
                    return;
                }
                scanner.nextLine();
                System.out.print("Track title: ");
                String title = scanner.nextLine();

                System.out.print("Artist name: ");
                String artistName = scanner.nextLine();

                System.out.print("Artist country: ");
                String country = scanner.nextLine();

                System.out.print("Genre (ROCK, POP, JAZZ, CLASSICAL, ELECTRONIC, HIPHOP, OTHER): ");
                String genreName = scanner.nextLine().toUpperCase();
                Genre genre;
                try {
                    genre = Genre.valueOf(genreName);
                } catch (IllegalArgumentException e) {
                    genre = Genre.OTHER;
                }

                System.out.print("Duration (seconds): ");
                int duration = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Record audio? (y/n): ");
                String choice = scanner.nextLine().trim().toLowerCase();
                String filePath = null;
                if (choice.equals("y")) {
                    filePath = "record_" + title.replaceAll("\\s+", "_") + ".wav";
                    AudioRecorder.recordAudio(filePath, duration);
                }

                Artist artist = new Artist(artistName, country);
                Track track = new Track(title, artist, genre, duration, filePath);
                album.addTrack(track);
                System.out.println("✅ Track added: " + track);
            }
        });

        // Create playlist
        menu.put(3, new MenuCommand("Create playlist") {
            public void execute() {
                if (album == null || album.getTracks().isEmpty()) {
                    System.out.println("❌ Album or tracks not available!");
                    return;
                }
                scanner.nextLine();
                System.out.print("Enter playlist name: ");
                String plName = scanner.nextLine();
                Playlist pl = new Playlist(plName);

                System.out.println("Select tracks to add to playlist (comma-separated indexes):");
                List<Track> tracks = album.getTracks();
                for (int i = 0; i < tracks.size(); i++)
                    System.out.println(i + ": " + tracks.get(i));
                System.out.print("Indexes: ");
                String[] idxs = scanner.nextLine().split(",");
                for (String s : idxs) {
                    try {
                        int idx = Integer.parseInt(s.trim());
                        if (idx >= 0 && idx < tracks.size())
                            pl.addTrack(tracks.get(idx));
                    } catch (Exception ignored) {
                    }
                }

                playlists.put(plName, pl);
                System.out.println("✅ Playlist created: " + pl);
            }
        });

        // Play playlist
        menu.put(4, new MenuCommand("Play playlist") {
            public void execute() {
                if (playlists.isEmpty()) {
                    System.out.println("❌ No playlists available!");
                    return;
                }
                scanner.nextLine();
                System.out.println("Available playlists:");
                int i = 0;
                List<String> names = new ArrayList<>(playlists.keySet());
                for (String name : names)
                    System.out.println(i++ + ": " + name);
                System.out.print("Select playlist index: ");
                try {
                    int choiceIdx = Integer.parseInt(scanner.nextLine());
                    if (choiceIdx < 0 || choiceIdx >= names.size()) {
                        System.out.println("Invalid selection.");
                        return;
                    }
                    Playlist pl = playlists.get(names.get(choiceIdx));
                    System.out.println("▶ Playing playlist: " + pl.getName());
                    for (Track t : pl.getTracks()) {
                        if (t.getFilePath() != null)
                            AudioPlayer.playWav(t.getFilePath());
                        else
                            System.out.println("Skipping track without audio: " + t);
                    }
                    System.out.println("✅ Playlist finished.");
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        });

        // Show album
        menu.put(5, new MenuCommand("Show album") {
            public void execute() {
                if (album == null)
                    System.out.println("❌ No album created!");
                else
                    service.printAlbum();
            }
        });

        // Sort tracks by genre
        menu.put(6, new MenuCommand("Sort tracks by genre") {
            public void execute() {
                if (album == null)
                    System.out.println("❌ No album created!");
                else {
                    service.sortByGenre();
                    System.out.println("✅ Tracks sorted by genre.");
                }
            }
        });

        // Save album to file
        menu.put(7, new MenuCommand("Save album to file") {
            public void execute() {
                if (album == null || service == null) {
                    System.out.println("❌ No album to save!");
                    return;
                }

                scanner.nextLine();
                System.out.print("Enter filename to save album: ");
                String filename = scanner.nextLine().trim();

                try {
                    service.saveToFile(filename);
                    System.out.println("✅ Album saved to " + filename);
                } catch (Exception e) {
                    System.out.println("❌ Error saving album: " + e.getMessage());
                }
            }
        });

        // Load album from file
        menu.put(8, new MenuCommand("Load album from file") {
            public void execute() {
                scanner.nextLine();
                System.out.print("Enter filename to load album: ");
                String filename = scanner.nextLine().trim();

                try {
                    Album loadedAlbum = new Album("Loaded Album");
                    service = new AlbumService(loadedAlbum);
                    service.loadFromFile(filename);
                    album = loadedAlbum;

                    System.out.println("✅ Album successfully loaded from " + filename);
                    service.printAlbum();
                } catch (Exception e) {
                    System.out.println("❌ Error loading album: " + e.getMessage());
                }
            }
        });

        // ===== Main loop =====
        while (true) {
            System.out.println("\n===== MENU =====");
            for (Map.Entry<Integer, MenuCommand> entry : menu.entrySet()) {
                System.out.println(entry.getKey() + " - " + entry.getValue().getName());
            }
            System.out.print("Select option: ");
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input.");
                scanner.next();
                continue;
            }
            int choice = scanner.nextInt();
            MenuCommand cmd = menu.get(choice);
            if (cmd != null) cmd.execute();
            else System.out.println("No such command.");
        }
    }
}
