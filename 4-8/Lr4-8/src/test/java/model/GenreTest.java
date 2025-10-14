package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GenreTest {
    @Test
    void testEnumValuesAndSerialization() {
        assertEquals("ROCK", Genre.ROCK.name());
        assertEquals(7, Genre.values().length);
    }
}
