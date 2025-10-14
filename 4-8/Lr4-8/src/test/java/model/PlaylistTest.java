package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlaylistTest {
    private Playlist playlist;
    private Track t1, t2;

    @BeforeEach
    void setUp() {
        playlist = new Playlist("My Playlist");
        Artist a = new Artist("Adele", "UK");
        t1 = new Track("Hello", a, Genre.POP, 300);
        t2 = new Track("Skyfall", a, Genre.POP, 290);
    }

    @Test
    void testAddAndPlayNext() {
        playlist.addTrack(t1);
        playlist.addTrack(t2);
        assertEquals(2, playlist.getTracks().size());
        assertEquals(t1, playlist.playNext());
        assertEquals(1, playlist.getTracks().size());
    }

    @Test
    void testGetTracksIsUnmodifiable() {
        playlist.addTrack(t1);
        var list = playlist.getTracks();
        assertThrows(UnsupportedOperationException.class, () -> list.add(t2));
    }

    @Test
    void testToString() {
        assertTrue(playlist.toString().contains("Playlist"));
    }

    @Test
    void testLombokGettersAndSetters() {
        playlist.setName("Updated");
        assertEquals("Updated", playlist.getName());
    }
}
