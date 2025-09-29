package command;

import model.*;
import service.AlbumService;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class LoadFromFileCommandTest {

    @Test
    void testExecuteSuccessfulLoad() throws Exception {
        // create temp file with album data
        Path tempFile = Files.createTempFile("album", ".txt");
        try (FileWriter writer = new FileWriter(tempFile.toFile())) {
            writer.write("Song1|Artist1|USA|ROCK|120\n");
            writer.write("Song2|Artist2|UK|POP|200\n");
        }

        Album album = new Album("LoadedAlbum");
        AlbumService service = new AlbumService(album);

        LoadFromFileCommand cmd = new LoadFromFileCommand(service, tempFile.toString());
        assertTrue(cmd.execute()); // returns true always

        assertEquals(2, album.getTracks().size());
        assertEquals("Song1", album.getTracks().get(0).getTitle());
        assertEquals("Song2", album.getTracks().get(1).getTitle());
    }

    @Test
    void testExecuteWithError() {
        Album album = new Album("BadAlbum");
        AlbumService service = new AlbumService(album);

        LoadFromFileCommand cmd = new LoadFromFileCommand(service, "non_existent_file.txt");
        assertTrue(cmd.execute()); // still returns true, but prints error
        assertTrue(album.getTracks().isEmpty());
    }

    @Test
    void testDescription() {
        Album album = new Album("Test");
        AlbumService service = new AlbumService(album);
        LoadFromFileCommand cmd = new LoadFromFileCommand(service, "file.txt");

        assertEquals("Load album from file", cmd.getDescription());
    }
}
