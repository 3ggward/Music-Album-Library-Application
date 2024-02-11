package model;

import org.json.JSONObject;
import persistence.Writable;

import java.time.*;

// represents a music album with a name, artist, year released, list of genres, a given rating, and a favourite status
// json persistence code based off of code in JsonSerializationDemo example
public class Album implements Writable {
    private String name;
    private String artist;
    private int dateYear;
    private int dateMonth;
    private int dateDay;
    private LocalDate date;
    private String genre;
    private int rating;
    private boolean favouriteStatus;

    // EFFECTS: creates new album with given name, artist, date released, and genre,
    // and initializes favourite status as false and rating at 0
    public Album(String name, String artist, int yearReleased, int monthReleased, int dayReleased, String genre) {
        this.name = name;
        this.artist = artist;
        dateYear = yearReleased;
        dateMonth = monthReleased;
        dateDay = dayReleased;
        this.date = LocalDate.of(yearReleased, monthReleased, dayReleased);
        this.genre = genre;
        rating = 0;
        favouriteStatus = false;
        EventLog.getInstance().logEvent(new Event("album, " + this.name + ", was created."));
    }

    // REQUIRES: 0 <= rating <= 5
    // MODIFIES: this
    // EFFECTS: sets rating of album to given rating, 0 means not rated yet
    public void rate(int rating) {
        this.rating = rating;
        EventLog.getInstance().logEvent(new Event("album, " + this.name + ", was rated " + rating + " stars."));
    }

    // MODIFIES: this
    // EFFECTS: sets favourite status of album to true if favourite status is false and vice versa
    public void favourite() {
        this.favouriteStatus = !favouriteStatus;

        if (favouriteStatus) {
            EventLog.getInstance().logEvent(new Event("album, " + this.name + ", was set as favourite."));
        } else {
            EventLog.getInstance().logEvent(new Event("album, " + this.name + ", was set as not favourite."));
        }
    }

    // getter and setter methods
    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public int getYear() {
        return dateYear;
    }

    public int getMonth() {
        return dateMonth;
    }

    public int getDay() {
        return dateDay;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getGenre() {
        return genre;
    }

    public int getRating() {
        return rating;
    }

    public boolean getFavouriteStatus() {
        return favouriteStatus;
    }

    public void setName(String name) {
        EventLog.getInstance().logEvent(new Event("album, " + this.name + ", set name to " + name));
        this.name = name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
        EventLog.getInstance().logEvent(new Event("album, " + this.name + ", set artist to " + artist));
    }

    public void setDate(int year, int month, int day) {
        dateYear = year;
        dateMonth = month;
        dateDay = day;
        this.date = LocalDate.of(year, month, day);
        EventLog.getInstance().logEvent(new Event("album, " + this.name + ", changed date to "
                + Integer.toString(month) + "/" + Integer.toString(day) + "/" + Integer.toString(year)));
    }

    public void setGenre(String genre) {
        this.genre = genre;
        EventLog.getInstance().logEvent(new Event("album, " + this.name + ", set genre to " + genre));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("artist", artist);
        json.put("year", dateYear);
        json.put("month", dateMonth);
        json.put("day", dateDay);
        json.put("genre", genre);
        json.put("rating", rating);
        json.put("favouriteStatus", favouriteStatus);
        return json;
    }
}
