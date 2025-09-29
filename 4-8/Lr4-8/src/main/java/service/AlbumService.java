package service;

import model.Album;
import model.Artist;
import model.Genre;
import model.Track;

import java.io.IOException;
import java.util.List;

public class AlbumService {
    private Album album;
    public AlbumService(Album album) { this.album = album; }

    public void loadFromFile(String path) throws IOException {
        List<String> lines = java.nio.file.Files.readAllLines(java.nio.file.Path.of(path));
        album.getTracks().clear();
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split("\\|");
            if (parts.length < 5) continue;
            String title = parts[0];
            String artistName = parts[1];
            String artistCountry = parts[2];
            Genre genre = Genre.valueOf(parts[3].toUpperCase());
            int dur = Integer.parseInt(parts[4]);
            Artist artist = new Artist(artistName, artistCountry);
            album.addTrack(new Track(title, artist, genre, dur));
        }
    }

    public void printAlbum() {
        System.out.println("Album: " + album.getName());
        for (Track t : album.getTracks()) {
            System.out.println(t);
        }
        int total = album.totalDuration();
        System.out.println("Total: " + total/60 + ":" + total%60);
    }

    public void sortByGenre() { album.sortByGenre(); }
    public List<Track> findByDurationRange(int min, int max) { return album.findByDurationRange(min,max); }


}
