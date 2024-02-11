package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AlbumTest {
    Album testAlbum;

    @BeforeEach
    void runBefore() {
        testAlbum = new Album("In Rainbows", "Radiohead", 2007, 10, 10, "Alternative");
    }

    @Test
    void albumConstructorTest() {
        LocalDate expectedDate = LocalDate.of(2007, 10, 10);

        assertEquals("In Rainbows", testAlbum.getName());
        assertEquals("Radiohead", testAlbum.getArtist());
        assertEquals(expectedDate, testAlbum.getDate());
        assertEquals("Alternative", testAlbum.getGenre());
        assertFalse(testAlbum.getFavouriteStatus());
        assertEquals(0, testAlbum.getRating());
    }

    @Test
    void rateTest() {
        testAlbum.rate(3);
        assertEquals(3, testAlbum.getRating());

        testAlbum.rate(5);
        assertEquals(5, testAlbum.getRating());
    }

    @Test
    void favouriteTest() {
        testAlbum.favourite();
        assertTrue(testAlbum.getFavouriteStatus());
        testAlbum.favourite();
        assertFalse(testAlbum.getFavouriteStatus());
        testAlbum.favourite();
        assertTrue(testAlbum.getFavouriteStatus());
    }

    @Test
    void setterTests() {
        testAlbum.setName("Stratosphere");
        assertEquals("Stratosphere", testAlbum.getName());
        testAlbum.setArtist("Duster");
        assertEquals("Duster", testAlbum.getArtist());
        testAlbum.setGenre("Slowcore");
        assertEquals("Slowcore", testAlbum.getGenre());
        testAlbum.setDate(1998, 2, 24);
        LocalDate expectedDate = LocalDate.of(1998, 2, 24);
        assertEquals(expectedDate, testAlbum.getDate());

        testAlbum.setName("Metaphorical Music");
        assertEquals("Metaphorical Music", testAlbum.getName());
        testAlbum.setArtist("Nujabes");
        assertEquals("Nujabes", testAlbum.getArtist());
        testAlbum.setGenre("Slowcore");
        assertEquals("Slowcore", testAlbum.getGenre());
        testAlbum.setDate(2003, 8, 21);
        expectedDate = LocalDate.of(2003, 8, 21);
        assertEquals(expectedDate, testAlbum.getDate());
    }
    
    @Test
    void toJsonTest() {
        JSONObject json = testAlbum.toJson();
        assertEquals(json.get("name"), testAlbum.getName());
        assertEquals(json.get("artist"), testAlbum.getArtist());
        assertEquals(json.get("year"), testAlbum.getYear());
        assertEquals(json.get("month"), testAlbum.getMonth());
        assertEquals(json.get("day"), testAlbum.getDay());
        assertEquals(json.get("genre"), testAlbum.getGenre());
        assertEquals(json.get("rating"), testAlbum.getRating());
        assertEquals(json.get("favouriteStatus"), testAlbum.getFavouriteStatus());
    }
}