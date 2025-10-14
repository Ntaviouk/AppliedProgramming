package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ArtistTest {
    @Test
    void testToString() {
        Artist a = new Artist("Eminem", "USA");
        assertEquals("Eminem (USA)", a.toString());
    }

    @Test
    void testLombokGeneratedMethods() {
        Artist a = new Artist("A", "B");
        a.setName("New");
        a.setCountry("C");
        assertEquals("New", a.getName());
        assertEquals("C", a.getCountry());
    }
}
