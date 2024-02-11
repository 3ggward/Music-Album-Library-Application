package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CatalogueTest {
    Catalogue testCatalogue;
    Album testAlbum1;
    Album testAlbum2;

    @BeforeEach
    void runBefore() {
        testCatalogue = new Catalogue("My Catalogue");
        testAlbum1 = new Album("In Rainbows", "Radiohead", 2007, 10, 10, "Alternative");
        testAlbum2 = new Album("For Lovers", "Lamp", 2004, 2, 11, "Shibuya-Kei");
    }

    @Test
    void catalogueConstructorTest() {
        assertTrue(testCatalogue.getAlbums().isEmpty());
        assertEquals(0, testCatalogue.getAlbums().size());
        assertEquals("My Catalogue", testCatalogue.getName());
    }

    @Test
    void addAlbumTestSingle() {
        testCatalogue.addAlbum(testAlbum1);

        assertFalse(testCatalogue.getAlbums().isEmpty());
        assertEquals(1, testCatalogue.getAlbums().size());
        assertEquals(testAlbum1, testCatalogue.getAlbums().get(0));
    }

    @Test
    void addAlbumTestMultiple() {
        testCatalogue.addAlbum(testAlbum1);
        testCatalogue.addAlbum(testAlbum2);

        assertFalse(testCatalogue.getAlbums().isEmpty());
        assertEquals(2, testCatalogue.getAlbums().size());
        assertEquals(testAlbum1, testCatalogue.getAlbums().get(0));
        assertEquals(testAlbum2, testCatalogue.getAlbums().get(1));
    }

    @Test
    void removeAlbumTestNoAlbum() {
        assertFalse(testCatalogue.removeAlbum("Kind of Blue"));
        assertTrue(testCatalogue.getAlbums().isEmpty());
        assertEquals(0, testCatalogue.getAlbums().size());
    }

    @Test
    void removeAlbumTestNoAlbumsWithGivenName() {
        testCatalogue.addAlbum(testAlbum1);
        testCatalogue.addAlbum(testAlbum2);

        assertFalse(testCatalogue.removeAlbum("Kind of Blue"));
        assertEquals(2, testCatalogue.getAlbums().size());
        assertEquals(testAlbum1, testCatalogue.getAlbums().get(0));
        assertEquals(testAlbum2, testCatalogue.getAlbums().get(1));
    }

    @Test
    void removeAlbumTestRemoveInOrder() {
        testCatalogue.addAlbum(testAlbum1);
        testCatalogue.addAlbum(testAlbum2);

        assertTrue(testCatalogue.removeAlbum("In Rainbows"));
        assertEquals(1, testCatalogue.getAlbums().size());
        assertEquals(testAlbum2, testCatalogue.getAlbums().get(0));

        assertTrue(testCatalogue.removeAlbum("For Lovers"));
        assertTrue(testCatalogue.getAlbums().isEmpty());
        assertEquals(0, testCatalogue.getAlbums().size());
    }

    @Test
    void removeAlbumTestRemoveOutOfOrder() {
        testCatalogue.addAlbum(testAlbum1);
        testCatalogue.addAlbum(testAlbum2);

        assertTrue(testCatalogue.removeAlbum("For Lovers"));
        assertEquals(1, testCatalogue.getAlbums().size());
        assertEquals(testAlbum1, testCatalogue.getAlbums().get(0));

        assertTrue(testCatalogue.removeAlbum("In Rainbows"));
        assertTrue(testCatalogue.getAlbums().isEmpty());
        assertEquals(0, testCatalogue.getAlbums().size());
    }

    @Test
    void setNameTest() {
        testCatalogue.setName("Edward's Albums");
        assertEquals("Edward's Albums", testCatalogue.getName());

        testCatalogue.setName("My best albums");
        assertEquals("My best albums", testCatalogue.getName());
    }
}
