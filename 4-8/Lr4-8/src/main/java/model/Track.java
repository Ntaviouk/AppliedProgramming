package model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Track implements Serializable {
    private String title;
    private Artist artist;
    private Genre genre;
    private int durationSeconds;

    @Override
    public String toString() {
        return artist + " - " + title + " [" + genre + "] (" + durationSeconds/60 + ":" + durationSeconds%60 + ")";
    }
}
