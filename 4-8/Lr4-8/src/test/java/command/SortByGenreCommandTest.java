package command;

import model.*;
import service.AlbumService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SortByGenreCommandTest {
    @Test
    void testExecute() {
        Album album = new Album("Test");
        Artist a = new Artist("X","Y");
        album.addTrack(new Track("Song1", a, Genre.ROCK, 100));
        album.addTrack(new Track("Song2", a, Genre.CLASSICAL, 200));

        AlbumService service = new AlbumService(album);
        SortByGenreCommand cmd = new SortByGenreCommand(service);

        assertTrue(cmd.execute());
        assertEquals(Genre.CLASSICAL, album.getTracks().get(0).getGenre());
    }
}
