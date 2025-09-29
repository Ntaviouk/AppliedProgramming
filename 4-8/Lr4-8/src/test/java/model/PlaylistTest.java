package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlaylistTest {
    @Test
    void testAddAndPlayNext() {
        Playlist playlist = new Playlist("MyPlaylist");
        Artist a = new Artist("X", "Y");
        Track track = new Track("Hit", a, Genre.ROCK, 200);

        playlist.addTrack(track);
        assertEquals(track, playlist.playNext());
        assertNull(playlist.playNext());
    }
}
