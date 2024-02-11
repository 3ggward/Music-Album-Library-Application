package persistence;

import model.Album;
import model.Catalogue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// some tests based off code in JsonSerializationDemo example
public class JsonWriterTest extends JsonTest {
    Album testAlbum1;
    Album testAlbum2;

    @BeforeEach
    void runBefore() {
        testAlbum1 = new Album("In Rainbows", "Radiohead", 2007, 10, 10, "Alternative");
        testAlbum2 = new Album("For Lovers", "Lamp", 2004, 2, 11, "Shibuya-Kei");
        testAlbum2.rate(3);
        testAlbum2.favourite();
    }

    @Test
    void testWriterInvalidFile() {
        try {
            Catalogue c = new Catalogue("My Catalogue");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyCatalogue() {
        try {
            Catalogue c = new Catalogue("My Catalogue");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCatalogue.json");
            writer.open();
            writer.write(c);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCatalogue.json");
            c = reader.read();
            assertEquals("My Catalogue", c.getName());
            assertEquals(0, c.getAlbums().size()); //rewrite
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralCatalogue() {
        try {
            Catalogue c = new Catalogue("My Catalogue");
            c.addAlbum(testAlbum1);
            c.addAlbum(testAlbum2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCatalogue.json");
            writer.open();
            writer.write(c);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCatalogue.json");
            c = reader.read();
            assertEquals("My Catalogue", c.getName());
            List<Album> albums = c.getAlbums();
            assertEquals(2, albums.size());
            checkAlbum("In Rainbows", "Radiohead", LocalDate.of(2007, 10, 10), "Alternative", 0, false, albums.get(0));
            checkAlbum("For Lovers", "Lamp", LocalDate.of(2004, 2, 11), "Shibuya-Kei", 3, true, albums.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
