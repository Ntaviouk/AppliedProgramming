package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TrackTest {
    @Test
    void testToStringWithoutFile() {
        Track t = new Track("Song", new Artist("ABBA", "Sweden"), Genre.POP, 125);
        String s = t.toString();
        assertTrue(s.contains("ABBA"));
        assertTrue(s.contains("2:05"));
    }

    @Test
    void testToStringWithFile() {
        Track t = new Track("Live", new Artist("Muse", "UK"), Genre.ROCK, 360, "C:/music/live.mp3");
        String s = t.toString();
        assertTrue(s.contains("🎵"));
        assertTrue(s.contains("live.mp3"));
    }

    @Test
    void testLombokGettersSetters() {
        Artist artist = new Artist("Coldplay", "UK");
        Track t = new Track("Fix You", artist, Genre.ROCK, 300);
        t.setFilePath("file.wav");
        assertEquals("file.wav", t.getFilePath());
        t.setDurationSeconds(301);
        assertEquals(301, t.getDurationSeconds());
    }
}
