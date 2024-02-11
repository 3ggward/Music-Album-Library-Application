//package ui;
//
//import model.Album;
//import model.Catalogue;
//import persistence.JsonReader;
//import persistence.JsonWriter;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.List;
//import java.util.ArrayList;
//import java.util.Scanner;
//
//// Album catalogue application, user interface code based off of code in accountTellerApp example
//// persistence code based off of code in JsonSerializationDemo example
//public class CatalogueApp {
//    private static final String JSON_STORE = "./data/catalogue.json";
//    private Catalogue myCatalogue;
//    private Scanner input;
//    private Scanner select;
//    private JsonWriter jsonWriter;
//    private JsonReader jsonReader;
//
//    // EFFECTS: runs the teller application
//    public CatalogueApp() {
//        runCatalogue();
//    }
//
//    // MODIFIES: this
//    // EFFECTS: processes user input
//    private void runCatalogue() {
//        boolean keepGoing = true;
//        String command;
//
//        init();
//
//        while (keepGoing) {
//            displayMenu();
//            command = input.next();
//
//            if (command.equals("quit")) {
//                keepGoing = false;
//            } else {
//                processCommand(command);
//            }
//        }
//
//        System.out.println("\nGoodbye!");
//    }
//
//    // MODIFIES: this
//    // EFFECTS: processes user command
//    private void processCommand(String command) {
//        if (command.equals("add")) {
//            doAdd();
//        } else if (command.equals("load")) {
//            loadCatalogue();
//        } else if (myCatalogue.getAlbums().size() == 0) {
//            System.out.println("Add an album before using other options!");
//            doAdd();
//        } else if (command.equals("view")) {
//            doView();
//        } else if (command.equals("info")) {
//            doInfo();
//        } else if (command.equals("edit")) {
//            doEdit();
//        } else if (command.equals("remove")) {
//            doRemove();
//        } else if (command.equals("rate")) {
//            doRate();
//        } else if (command.equals("fav")) {
//            doFav();
//        } else if (command.equals("save")) {
//            saveCatalogue();
//        } else {
//            System.out.println("Selection not valid...");
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: initializes catalogue and inputs
//    private void init() {
//        myCatalogue = new Catalogue("My Catalogue");
//        input = new Scanner(System.in);
//        select = new Scanner(System.in);
//        input.useDelimiter("\n");
//        jsonWriter = new JsonWriter(JSON_STORE);
//        jsonReader = new JsonReader(JSON_STORE);
//    }
//
//    // EFFECTS: displays menu of options to user
//    private void displayMenu() {
//        System.out.println("\nSelect Option:");
//        System.out.println("\tview -> view catalogue");
//        System.out.println("\tinfo -> view album info");
//        System.out.println("\tedit -> edit album info");
//        System.out.println("\tadd -> add album to catalogue");
//        System.out.println("\tremove -> remove album from catalogue");
//        System.out.println("\trate -> rate album from catalogue");
//        System.out.println("\tfav -> toggle album from catalogue as favourite or not favourite");
//        System.out.println("\tsave -> save work room to file");
//        System.out.println("\tload -> load work room from file");
//        System.out.println("\tquit -> quit");
//    }
//
//    // EFFECTS: displays names of all albums in catalogue
//    private void doView() {
//        List<String> albumListNames = new ArrayList<>();
//
//        for (Album a : myCatalogue.getAlbums()) {
//            albumListNames.add(a.getName());
//        }
//
//        System.out.println(albumListNames);
//    }
//
//    // EFFECTS: displays info of selected album
//    private void doInfo() {
//        Album selected = selectAlbum();
//        System.out.print("\n" + selected.getName() + " by " + selected.getArtist());
//        System.out.println("\nReleased on " + selected.getDate());
//        System.out.print("Falls within the genre of " + selected.getGenre());
//
//        if (selected.getRating() == 0) {
//            System.out.println("\nYou have not rated this album yet");
//        } else {
//            System.out.println("\nYou have rated this album " + selected.getRating() + " stars out of 5 stars.");
//        }
//
//        if (selected.getFavouriteStatus()) {
//            System.out.println("You have this album listed as one of your favourites.");
//        }
//    }
//
//    // MODIFIES: this, selected album
//    // EFFECTS: allows user to edit name, artist, genre, or date released of selected album
//    private void doEdit() {
//        Album selected = selectAlbum();
//        String command;
//
//        System.out.println("\nSelect Option:");
//        System.out.println("\tname -> edit album name");
//        System.out.println("\tartist -> edit album artist");
//        System.out.println("\tgenre -> edit album genre");
//        System.out.println("\tdate -> edit album date");
//
//        command = input.next();
//
//        if (command.equals("name")) {
//            changeAlbumName(selected);
//        } else if (command.equals("artist")) {
//            changeAlbumArtist(selected);
//        } else if (command.equals("genre")) {
//            changeAlbumGenre(selected);
//        } else if (command.equals("date")) {
//            changeAlbumDate(selected);
//        } else {
//            System.out.println("Selection not valid...");
//        }
//    }
//
//    // MODIFIES: selected album
//    // EFFECTS: allows user to edit name selected album
//    private void changeAlbumName(Album album) {
//        String change;
//
//        System.out.println("\nEnter new name: ");
//        change = input.next();
//
//        album.setName(change);
//    }
//
//    // MODIFIES: selected album
//    // EFFECTS: allows user to edit artist selected album
//    private void changeAlbumArtist(Album album) {
//        String change;
//
//        System.out.println("\nEnter new artist: ");
//        change = input.next();
//
//        album.setArtist(change);
//    }
//
//    // MODIFIES: selected album
//    // EFFECTS: allows user to edit name selected album
//    private void changeAlbumGenre(Album album) {
//        String change;
//
//        System.out.println("\nEnter new genre: ");
//        change = input.next();
//
//        album.setGenre(change);
//    }
//
//    // MODIFIES: selected album
//    // EFFECTS: allows user to edit name selected album
//    private void changeAlbumDate(Album album) {
//        int newYear;
//        int newMonth;
//        int newDay;
//
//        System.out.println("\nEnter new year (4-digit integer): ");
//        newYear = input.nextInt();
//
//        System.out.println("\nEnter new month (1-12): ");
//        newMonth = input.nextInt();
//
//        System.out.println("\nEnter new day (1-31): ");
//        newDay = input.nextInt();
//
//        album.setDate(newYear, newMonth, newDay);
//    }
//
//    // MODIFIES: this
//    // EFFECTS: creates album with given info, and adds to your catalogue
//    private void doAdd() {
//        String albumName;
//        String albumArtist;
//        int yearReleased;
//        int monthReleased;
//        int dayReleased;
//        String albumGenre;
//
//        System.out.print("Enter name of album: ");
//        albumName = input.next();
//
//        System.out.print("Enter artist of album: ");
//        albumArtist = input.next();
//
//        System.out.print("Enter year of album release (4-digit integer): ");
//        yearReleased = input.nextInt();
//
//        System.out.print("Enter month of album release (integer from 1-12): ");
//        monthReleased = input.nextInt();
//
//        System.out.print("Enter day of album release (integer from 1-31): ");
//        dayReleased = input.nextInt();
//
//        System.out.print("Enter genre of album: ");
//        albumGenre = input.next();
//
//        Album newAlbum = new Album(albumName, albumArtist, yearReleased, monthReleased, dayReleased, albumGenre);
//
//        myCatalogue.addAlbum(newAlbum);
//
//        System.out.println("Album has been created and added to catalogue!");
//    }
//
//    // MODIFIES: this
//    // EFFECTS: removes selected album from catalogue
//    private void doRemove() {
//        Album selected = selectAlbum();
//
//        myCatalogue.getAlbums().remove(selected);
//
//        System.out.println(selected.getName() + " has been removed from the catalogue.");
//    }
//
//    // MODIFIES: this
//    // EFFECTS: rates selected album from 0 to 5
//    private void doRate() {
//        Album selected = selectAlbum();
//        System.out.print("Enter rating: ");
//        int rating = input.nextInt();
//
//        selected.rate(rating);
//
//        System.out.println(selected.getName() + " has been rated " + rating + " stars out of 5 stars.");
//    }
//
//    // MODIFIES: this
//    // EFFECTS: toggles selected album as a favourite or not a favourite
//    private void doFav() {
//        Album selected = selectAlbum();
//
//        selected.favourite();
//
//        if (selected.getFavouriteStatus()) {
//            System.out.println(selected.getName() + " has been set as a favourite!");
//        } else {
//            System.out.println(selected.getName() + " is no longer set as a favourite.");
//        }
//    }
//
//    // EFFECTS: saves the workroom to file
//    private void saveCatalogue() {
//        try {
//            jsonWriter.open();
//            jsonWriter.write(myCatalogue);
//            jsonWriter.close();
//            System.out.println("Saved " + myCatalogue.getName() + " to " + JSON_STORE);
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to write to file: " + JSON_STORE);
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: loads workroom from file
//    private void loadCatalogue() {
//        try {
//            myCatalogue = jsonReader.read();
//            System.out.println("Loaded " + myCatalogue.getName() + " from " + JSON_STORE);
//        } catch (IOException e) {
//            System.out.println("Unable to read from file: " + JSON_STORE);
//        }
//    }
//
//    // EFFECTS: prompts user to select an album within catalogue
//    private Album selectAlbum() {
//        String selection;
//        List<Album> albumCatalogue = myCatalogue.getAlbums();
//
//        System.out.println("What is the name of the album you would like to select? ");
//        doView();
//
//        selection = select.nextLine();
//
//        for (Album a : albumCatalogue) {
//            if (selection.equals(a.getName())) {
//                System.out.println("Successfully selected album with given name.");
//                return a;
//            }
//        }
//
//        System.out.println("Album with given name does not exist in your library, please select again.");
//        return selectAlbum();
//    }
//}
//
