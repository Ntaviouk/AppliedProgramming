package service;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlbumServiceTest {

    private Album album;
    private AlbumService albumService;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        album = new Album("Test Album");
        albumService = new AlbumService(album);

        Artist artist1 = new Artist("Queen", "UK");
        Artist artist2 = new Artist("Daft Punk", "France");

        album.addTrack(new Track("Bohemian Rhapsody", artist1, Genre.ROCK, 355));
        album.addTrack(new Track("Another One Bites the Dust", artist1, Genre.ROCK, 215));
        album.addTrack(new Track("Around the World", artist2, Genre.ELECTRONIC, 429));
        album.addTrack(new Track("Sonata No. 14", new Artist("Beethoven", "Germany"), Genre.CLASSICAL, 360));
    }

    @Test
    void testSaveAndLoadFromFile() throws IOException {
        Path filePath = tempDir.resolve("testAlbum.txt");

        albumService.saveToFile(filePath.toString());

        assertTrue(Files.exists(filePath));
        assertTrue(Files.size(filePath) > 0);

        Album newAlbum = new Album("Loaded Album");
        AlbumService newService = new AlbumService(newAlbum);

        newService.loadFromFile(filePath.toString());

        assertEquals(4, newAlbum.getTracks().size());
        assertEquals("Bohemian Rhapsody", newAlbum.getTracks().get(0).getTitle());
        assertEquals("Daft Punk", newAlbum.getTracks().get(2).getArtist().getName());
        assertEquals(Genre.CLASSICAL, newAlbum.getTracks().get(3).getGenre());
    }

    @Test
    void testFindByDurationRange() {
        List<Track> result = albumService.findByDurationRange(300, 400);

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(t -> t.getTitle().equals("Bohemian Rhapsody")));
        assertTrue(result.stream().anyMatch(t -> t.getTitle().equals("Sonata No. 14")));

        List<Track> emptyResult = albumService.findByDurationRange(1000, 2000);

        assertTrue(emptyResult.isEmpty());
    }

    @Test
    void testSortByGenre() {
        albumService.sortByGenre();
        List<Track> sortedTracks = album.getTracks();

        assertEquals(Genre.CLASSICAL, sortedTracks.get(0).getGenre());
        assertEquals(Genre.ELECTRONIC, sortedTracks.get(1).getGenre());
        assertEquals(Genre.ROCK, sortedTracks.get(2).getGenre());
        assertEquals(Genre.ROCK, sortedTracks.get(3).getGenre());
    }

    @Test
    void testPrintAlbum() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        albumService.printAlbum();

        String output = outContent.toString();
        assertTrue(output.contains("Album: Test Album"));
        assertTrue(output.contains("Bohemian Rhapsody"));
        assertTrue(output.contains("Total duration: 22:39")); // 355+215+429+360 = 1359 сек = 22:39

        System.setOut(System.out);
    }
}