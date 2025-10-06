package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;

/**
 * Музичний трек.
 */
@Data
@AllArgsConstructor
public class Track implements Serializable {
    private String title;
    private Artist artist;
    private Genre genre;
    private int durationSeconds;

    @Override
    public String toString() {
        return String.format("%s - %s [%s] (%d:%02d)",
                artist,
                title,
                genre,
                durationSeconds / 60,
                durationSeconds % 60);
    }
}
