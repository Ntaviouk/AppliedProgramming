package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class Track implements Serializable {
    private String title;
    private Artist artist;
    private Genre genre;
    private int durationSeconds;
    private String filePath;

    public Track(String title, Artist artist, Genre genre, int durationSeconds) {
        this(title, artist, genre, durationSeconds, null);
    }

    @Override
    public String toString() {
        String base = artist + " - " + title + " [" + genre + "] (" +
                durationSeconds / 60 + ":" + String.format("%02d", durationSeconds % 60) + ")";
        if (filePath != null) base += " 🎵 [recorded: " + new File(filePath).getName() + "]";
        return base;
    }
}
