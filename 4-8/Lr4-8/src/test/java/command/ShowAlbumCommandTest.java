package command;

import model.*;
import service.AlbumService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShowAlbumCommandTest {

    @Test
    void testExecute() {
        Album album = new Album("MyAlbum");
        Artist artist = new Artist("A", "C");
        album.addTrack(new Track("Song1", artist, Genre.ROCK, 120));

        AlbumService service = new AlbumService(album);
        ShowAlbumCommand cmd = new ShowAlbumCommand(service);

        assertTrue(cmd.execute()); // always returns true
    }

    @Test
    void testDescription() {
        Album album = new Album("EmptyAlbum");
        AlbumService service = new AlbumService(album);
        ShowAlbumCommand cmd = new ShowAlbumCommand(service);

        assertEquals("Show album", cmd.getDescription());
    }
}
