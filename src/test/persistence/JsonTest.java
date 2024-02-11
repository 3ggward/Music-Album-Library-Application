package persistence;

import model.Album;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkAlbum(String name, String artist, LocalDate date, String genre, int rating, boolean favouriteStatus, Album album) {
        assertEquals(name, album.getName());
        assertEquals(artist, album.getArtist());
        assertEquals(date, album.getDate());
        assertEquals(genre, album.getGenre());
        assertEquals(rating, album.getRating());
        assertEquals(favouriteStatus, album.getFavouriteStatus());
    }
}
