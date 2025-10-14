package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AlbumTest {
    private Album album;
    private Artist artist;

    @BeforeEach
    void setUp() {
        artist = new Artist("Queen", "UK");
        album = new Album("Greatest Hits");
        album.addTrack(new Track("Bohemian Rhapsody", artist, Genre.ROCK, 354));
        album.addTrack(new Track("Another One Bites the Dust", artist, Genre.POP, 215));
        album.addTrack(new Track("Jazz Tune", artist, Genre.JAZZ, 180));
    }

    @Test
    void testAddTrackAndTotalDuration() {
        assertEquals(3, album.getTracks().size());
        assertEquals(354 + 215 + 180, album.totalDuration());
    }

    @Test
    void testSortByGenre() {
        album.sortByGenre();
        List<Genre> sorted = album.getTracks().stream().map(Track::getGenre).toList();
        assertEquals(List.of(Genre.JAZZ, Genre.POP, Genre.ROCK), sorted);
    }

    @Test
    void testFindByDurationRange() {
        List<Track> found = album.findByDurationRange(200, 300);
        assertEquals(1, found.size());
        assertEquals("Another One Bites the Dust", found.get(0).getTitle());
    }

    @Test
    void testLombokGettersAndSetters() {
        album.setName("Updated");
        assertEquals("Updated", album.getName());
    }

    @Test
    void testToStringFromLombok() {
        String s = album.toString();
        assertTrue(s.contains("name="));
    }
}
