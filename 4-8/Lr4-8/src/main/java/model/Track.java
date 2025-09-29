package model;

import java.io.Serializable;

public class Track implements Serializable {
    private String title;
    private Artist artist;
    private Genre genre;
    private int durationSeconds;

    public Track(String title, Artist artist, Genre genre, int durationSeconds) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.durationSeconds = durationSeconds;
    }

    public String getTitle() { return title; }
    public Artist getArtist() { return artist; }
    public Genre getGenre() { return genre; }
    public int getDurationSeconds() { return durationSeconds; }

    @Override
    public String toString() {
        return artist + " - " + title + " [" + genre + "] (" + durationSeconds/60 + ":" + durationSeconds%60 + ")";
    }
}
