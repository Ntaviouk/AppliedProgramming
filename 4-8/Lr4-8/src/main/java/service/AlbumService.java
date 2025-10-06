package service;

import lombok.Data;
import model.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Data
public class AlbumService {
    private final Album album;

    public AlbumService(Album album) {
        this.album = album;
    }


    public void loadFromFile(String path) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(path));
        album.getTracks().clear();

        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            String[] parts = line.split("\\|");
            if (parts.length < 5) continue;

            String title = parts[0];
            String artistName = parts[1];
            String artistCountry = parts[2];
            Genre genre = Genre.valueOf(parts[3].toUpperCase());
            int duration = Integer.parseInt(parts[4]);

            Artist artist = new Artist(artistName, artistCountry);
            album.addTrack(new Track(title, artist, genre, duration));
        }
    }


    public void printAlbum() {
        System.out.println("Album: " + album.getName());
        if (album.getTracks().isEmpty()) {
            System.out.println("No tracks in album.");
            return;
        }

        album.getTracks().forEach(System.out::println);
        int total = album.totalDuration();
        System.out.println("Total duration: " + formatTime(total));
    }


    public void sortByGenre() {
        album.sortByGenre();
    }


    public List<Track> findByDurationRange(int min, int max) {
        return album.findByDurationRange(min, max);
    }


    private String formatTime(int seconds) {
        return String.format("%d:%02d", seconds / 60, seconds % 60);
    }
}
