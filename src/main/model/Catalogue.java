package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.List;
import java.util.ArrayList;

// represents a music album catalogue, with given name and album catalogue
// json persistence code based off of code in JsonSerializationDemo example
public class Catalogue implements Writable {
    private String name;
    private List<Album> albumCatalogue;

    // EFFECTS: creates new catalogue with given name and an empty album list
    public Catalogue(String name) {
        this.name = name;
        albumCatalogue = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("catalogue, " + this.name + ", was created."));
    }

    // REQUIRES: given album to not already be in list
    // MODIFIES: this
    // EFFECTS: adds given album to catalogue in order added
    public void addAlbum(Album album) {
        albumCatalogue.add(album);
        EventLog.getInstance().logEvent(new Event("album, " + album.getName() + ", was added to myCatalogue."));
    }

    // MODIFIES: this
    // EFFECTS: removes album which matches given album name and returns true, otherwise returns false
    public boolean removeAlbum(String albumName) {
        for (Album a : albumCatalogue) {
            if (a.getName().equals(albumName)) {
                albumCatalogue.remove(a);
                EventLog.getInstance().logEvent(new Event("album, " + albumName + ", was removed from myCatalogue."));
                return true;
            }
        }
        return false;
    }

    // simple getter and setter methods
    public String getName() {
        return name;
    }

    public List<Album> getAlbums() {
        return albumCatalogue;
    }

    public void setName(String name) {
        EventLog.getInstance().logEvent(new Event("catalogue, " + this.name + " set name to " + name));
        this.name = name;
    }

    // EFFECTS: returns this catalogue as a JsonObject
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("albums", albumsToJson());
        return json;
    }

    // EFFECTS: returns things in this catalogue as a JSON array
    private JSONArray albumsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Album a : albumCatalogue) {
            jsonArray.put(a.toJson());
        }

        return jsonArray;
    }
}
