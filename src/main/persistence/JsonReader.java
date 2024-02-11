package persistence;

import model.Album;
import model.Catalogue;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads Catalogue from JSON data stored in file

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Catalogue from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Catalogue read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCatalogue(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Catalogue from JSON object and returns it
    private Catalogue parseCatalogue(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Catalogue c = new Catalogue(name);
        addAlbums(c, jsonObject);
        return c;
    }

    // MODIFIES: c
    // EFFECTS: parses albums from JSON object and adds them to catalogue
    private void addAlbums(Catalogue c, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("albums");
        for (Object json : jsonArray) {
            JSONObject nextThingy = (JSONObject) json;
            addAlbum(c, nextThingy);
        }
    }

    // MODIFIES: c
    // EFFECTS: parses thingy from JSON object and adds it to catalogue
    private void addAlbum(Catalogue c, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String artist = jsonObject.getString("artist");
        int dateYear = jsonObject.getInt("year");
        int dateMonth = jsonObject.getInt("month");
        int dateDay = jsonObject.getInt("day");
        String genre = jsonObject.getString("genre");
        int rating = jsonObject.getInt("rating");
        boolean favouriteStatus = jsonObject.getBoolean("favouriteStatus");

        Album album = new Album(name, artist, dateYear, dateMonth, dateDay, genre);
        album.rate(rating);
        if (favouriteStatus) {
            album.favourite();
        }

        c.addAlbum(album);
    }
}