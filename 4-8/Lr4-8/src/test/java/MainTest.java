package org.example;

import org.junit.jupiter.api.Test;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    private String runMainWithInput(String input) {
        InputStream sysInBackup = System.in;
        PrintStream sysOutBackup = System.out;
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            System.setIn(in);
            System.setOut(new PrintStream(out));

            Main.main(new String[]{});

            return out.toString();
        } finally {
            System.setIn(sysInBackup);
            System.setOut(sysOutBackup);
        }
    }

    @Test
    void testCreateAlbumAndExit() {
        String input = "1\nMyAlbum\n0\n";
        String output = runMainWithInput(input);
        assertTrue(output.contains("Album 'MyAlbum' created."));
    }

    @Test
    void testAddTrackToAlbum() {
        String input = ""
                + "1\nMyAlbum\n"
                + "2\nSong1\nArtist1\nCountry1\nROCK\n120\n"
                + "0\n";
        String output = runMainWithInput(input);
        assertTrue(output.contains("Track added."));
    }

    @Test
    void testCreatePlaylist() {
        String input = "3\nMyPlaylist\n0\n";
        String output = runMainWithInput(input);
        assertTrue(output.contains("Playlist 'MyPlaylist' created."));
    }

    @Test
    void testAddTrackNoAlbum() {
        String input = "2\n0\n";
        String output = runMainWithInput(input);
        assertTrue(output.contains("No album created."));
    }

    @Test
    void testShowAlbumNoAlbum() {
        String input = "5\n0\n";
        String output = runMainWithInput(input);
        assertTrue(output.contains("No album created."));
    }

    @Test
    void testSortAlbumNoAlbum() {
        String input = "6\n0\n";
        String output = runMainWithInput(input);
        assertTrue(output.contains("No album created."));
    }

    @Test
    void testFindByDurationNoAlbum() {
        String input = "7\n10\n20\n0\n";
        String output = runMainWithInput(input);
        assertTrue(output.contains("No album created."));
    }

    @Test
    void testSaveAlbumNoAlbum() {
        String input = "8\nfile.dat\n0\n";
        String output = runMainWithInput(input);
        assertTrue(output.contains("No album created."));
    }

    @Test
    void testLoadAlbumNonExistentFile() {
        String input = "10\nnon_existent_file.txt\n0\n";
        String output = runMainWithInput(input);
        assertTrue(output.contains("Error loading album:"));
    }

    @Test
    void testHelpAndExit() {
        String input = "9\n0\n";
        String output = runMainWithInput(input);
        assertTrue(output.contains("Help"));
        assertTrue(output.contains("Exit"));
    }

    @Test
    void testExitDirectly() {
        String input = "0\n";
        String output = runMainWithInput(input);
        assertTrue(output.contains("Exit"));
    }
}
