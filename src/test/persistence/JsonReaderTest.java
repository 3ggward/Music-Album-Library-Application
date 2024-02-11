package persistence;

import model.Album;
import model.Catalogue;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// some tests based off code in JsonSerializationDemo example
public class JsonReaderTest extends JsonTest{
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Catalogue c = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCatalogue() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCatalogue.json");
        try {
            Catalogue c = reader.read();
            assertEquals("My Catalogue", c.getName());
            assertEquals(0, c.getAlbums().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCatalogue() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCatalogue.json");
        try {
            Catalogue c = reader.read();
            assertEquals("My Catalogue", c.getName());
            List<Album> albums = c.getAlbums();
            assertEquals(2, albums.size());
            checkAlbum("In Rainbows", "Radiohead", LocalDate.of(2007, 10, 10), "Alternative", 0, false, albums.get(0));
            checkAlbum("For Lovers", "Lamp", LocalDate.of(2004, 2, 11), "Shibuya-Kei", 3, true, albums.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
