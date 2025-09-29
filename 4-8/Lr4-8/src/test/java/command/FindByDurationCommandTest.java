package command;

import model.*;
import service.AlbumService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FindByDurationCommandTest {

    private Album album;
    private AlbumService service;

    @BeforeEach
    void setUp() {
        album = new Album("TestAlbum");
        Artist artist = new Artist("TestArtist", "USA");
        album.addTrack(new Track("ShortSong", artist, Genre.ROCK, 100));
        album.addTrack(new Track("LongSong", artist, Genre.POP, 300));
        service = new AlbumService(album);
    }

    @Test
    void testExecuteWithResults() {
        FindByDurationCommand cmd = new FindByDurationCommand(service, 90, 150);
        assertTrue(cmd.execute());
        List<Track> results = service.findByDurationRange(90, 150);
        assertEquals(1, results.size());
        assertEquals("ShortSong", results.get(0).getTitle());
    }

    @Test
    void testExecuteNoResults() {
        FindByDurationCommand cmd = new FindByDurationCommand(service, 400, 500);
        assertTrue(cmd.execute());
        List<Track> results = service.findByDurationRange(400, 500);
        assertTrue(results.isEmpty());
    }

    @Test
    void testDescription() {
        FindByDurationCommand cmd = new FindByDurationCommand(service, 0, 1000);
        assertEquals("Find tracks by duration", cmd.getDescription());
    }
}
