package model;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AlbumTest {

    @Test
    void testAddAndTotalDuration() {
        Album album = new Album("TestAlbum");
        Artist artist = new Artist("Name", "Country");
        album.addTrack(new Track("Song1", artist, Genre.ROCK, 120));
        album.addTrack(new Track("Song2", artist, Genre.POP, 180));

        assertEquals(300, album.totalDuration());
    }

    @Test
    void testSortByGenre() {
        Album album = new Album("Test");
        Artist a = new Artist("A", "C");
        album.addTrack(new Track("RockSong", a, Genre.ROCK, 200));
        album.addTrack(new Track("JazzSong", a, Genre.JAZZ, 150));

        album.sortByGenre();
        assertEquals(Genre.JAZZ, album.getTracks().get(0).getGenre());
    }

    @Test
    void testFindByDurationRange() {
        Album album = new Album("Test");
        Artist a = new Artist("A", "C");
        Track t1 = new Track("Short", a, Genre.ROCK, 100);
        Track t2 = new Track("Long", a, Genre.POP, 300);
        album.addTrack(t1);
        album.addTrack(t2);

        List<Track> result = album.findByDurationRange(90, 150);
        assertEquals(1, result.size());
        assertEquals("Short", result.get(0).getTitle());
    }
}
