package ui;

import model.Album;
import model.Catalogue;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

// Based off of Java ListDemoProject

// A GUI representing a catalogue, with a list on the left, showing library of albums, info pane on the right, showing
// the info of the selected album from your library, and a button pane on the bottom which allows you to save, load,
// remove, or add albums from your catalogue.
public class CatalogueUI extends JPanel {
    private static final String JSON_STORE = "./data/catalogue.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Catalogue myCatalogue;
    private JPanel centerWindow;
    private JPanel listPane;
    private JList<Album> albumList;
    private DefaultListModel listModel;
    private JPanel infoPane;
    private JPanel buttonPane;
    private ImageIcon starIcon = new ImageIcon("./data/star.png");

    private JButton addButton;
    private JButton removeButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton editNameButton;
    private JButton editArtistButton;
    private JButton editYearButton;
    private JButton editMonthButton;
    private JButton editDayButton;
    private JButton editGenreButton;
    private JButton editRatingButton;
    private JButton toggleFavouriteButton;
    private EditListener editListener;
    private AddListener addListener;
    private JLabel nameTextLabel;
    private JLabel artistTextLabel;
    private JLabel yearTextLabel;
    private JLabel monthTextLabel;
    private JLabel dayTextLabel;
    private JLabel genreTextLabel;

    private JTextField albumName;
    private JTextField albumArtist;
    private JTextField albumYear;
    private JTextField albumMonth;
    private JTextField albumDay;
    private JTextField albumGenre;
    private JTextField editName;
    private JTextField editArtist;
    private JTextField editYear;
    private JTextField editMonth;
    private JTextField editDay;
    private JTextField editGenre;
    private JTextField editRating;

    private JLabel nameLabel;
    private JLabel artistLabel;
    private JLabel yearLabel;
    private JLabel monthLabel;
    private JLabel dayLabel;
    private JLabel genreLabel;
    private JLabel ratingLabel;
    private JLabel favouriteLabel;

    private JLabel selectedName;
    private JLabel selectedArtist;
    private JLabel selectedYear;
    private JLabel selectedMonth;
    private JLabel selectedDay;
    private JLabel selectedGenre;
    private JLabel selectedRating;
    private JLabel selectedFavourite;
    private JLabel selectedFavouriteIcon;

    // EFFECTS: sets up placement of ui components, initializes fields, initializes listeners and persistence
    public CatalogueUI() {
        super(new BorderLayout());
        myCatalogue = new Catalogue("myCatalogue");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        centerWindow = new JPanel(new GridLayout(1,2));
        listPane = new JPanel(new BorderLayout());
        infoPane = new JPanel(new GridLayout(8,4));
        buttonPane = new JPanel();
        Image starImg = starIcon.getImage(); // transform it
        Image scaledStarImg = starImg.getScaledInstance(80, 80,  java.awt.Image.SCALE_SMOOTH);
        starIcon = new ImageIcon(scaledStarImg);  // transform it back

        setupListPane();
        setupInfoPane();
        setupButtonPane();

        centerWindow.add(listPane);
        centerWindow.add(infoPane);

        this.add(centerWindow, BorderLayout.CENTER);
        this.add(buttonPane, BorderLayout.PAGE_END);
    }

    // MODIFIES: this
    // EFFECTS: initializes components needed for list and info, creates listlistener and places listpane on ui
    public void setupListPane() {
        listPaneCompInit();

        ListSelectionListener listListener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                    if (albumList.getSelectedIndex() == -1 || listModel.isEmpty()) {
                        //No selection, disable fire button.
                        removeButton.setEnabled(false);
                        infoPane.removeAll();
                    } else {
                        //Selection, enable the fire button.
                        removeButton.setEnabled(true);
                        setSelectedInfo();
                        placeInfo();
                    }
                }
        };

        albumList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        albumList.setSelectedIndex(0);
        albumList.addListSelectionListener(listListener);

        displayAlbumName();

        listPane.add(albumList, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: displays names of albums in Jlist
    private void displayAlbumName() {
        // displays name of album instead of album id within JList
        albumList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                                                          boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (renderer instanceof JLabel && value instanceof Album) {
                    // Here value will be of the Type 'Album'
                    ((JLabel) renderer).setText(((Album) value).getName());
                }
                return renderer;
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: initializes components needed for listPane
    public void listPaneCompInit() {
        listModel = new DefaultListModel<Album>();
        albumList = new JList<>(listModel);
        selectedName = new JLabel("");
        selectedArtist = new JLabel("");
        selectedYear = new JLabel("");
        selectedMonth = new JLabel("");
        selectedDay = new JLabel("");
        selectedGenre = new JLabel("");
        selectedRating = new JLabel("");
        selectedFavourite = new JLabel("");
        selectedFavouriteIcon = new JLabel();
    }

    // MODIFIES: this
    // EFFECTS: sets components for infoPane to match with info on selected album from albumList
    private void setSelectedInfo() {
        selectedName.setText(albumList.getSelectedValue().getName());
        selectedArtist.setText(albumList.getSelectedValue().getArtist());
        selectedYear.setText(Integer.toString(albumList.getSelectedValue().getYear()));
        selectedMonth.setText(Integer.toString(albumList.getSelectedValue().getMonth()));
        selectedDay.setText(Integer.toString(albumList.getSelectedValue().getDay()));
        selectedGenre.setText(albumList.getSelectedValue().getGenre());
        selectedRating.setText(Integer.toString(albumList.getSelectedValue().getRating()));

        if (albumList.getSelectedValue().getFavouriteStatus()) {
            selectedFavourite.setText("Yes");
            selectedFavouriteIcon.setIcon(starIcon);
        } else {
            selectedFavourite.setText("No");
            selectedFavouriteIcon.setIcon(null);
            selectedFavouriteIcon.revalidate();
        }
    }

    // MODIFIES: this
    // EFFECTS: places all rows of info shown on right side of the screen
    private void placeInfo() {
        placeInfoRow(nameLabel, selectedName, editName, editNameButton);
        placeInfoRow(artistLabel, selectedArtist, editArtist, editArtistButton);
        placeInfoRow(yearLabel, selectedYear, editYear, editYearButton);
        placeInfoRow(monthLabel, selectedMonth, editMonth, editMonthButton);
        placeInfoRow(dayLabel, selectedDay, editDay, editDayButton);
        placeInfoRow(genreLabel, selectedGenre, editGenre, editGenreButton);
        placeInfoRow(ratingLabel, selectedRating, editRating, editRatingButton);
        placeInfoRow(favouriteLabel, selectedFavourite, selectedFavouriteIcon, toggleFavouriteButton);
    }

    // MODIFIES: this
    // EFFECTS: places a row of info on infoPane, with given label, selected info label, textfield, and button
    private void placeInfoRow(JLabel label, JLabel selectedInfo, JComponent input, JButton editButton) {
        infoPane.add(label);
        infoPane.add(selectedInfo);
        infoPane.add(input);
        infoPane.add(editButton);
    }

    // MODIFIES: this
    // EFFECTS: initializes infoPane components, and sets colour
    private void setupInfoPane() {
        infoPaneCompInit();
        infoPane.setBackground(Color.GRAY);
        infoPane.removeAll();
    }

    // MODIFIES: this
    // EFFECTS: initializes components needed infoPane
    private void infoPaneCompInit() {
        nameLabel = new JLabel("Name: ");
        artistLabel = new JLabel("Artist: ");
        yearLabel = new JLabel("Year: ");
        monthLabel = new JLabel("Month: ");
        dayLabel = new JLabel("Day: ");
        genreLabel = new JLabel("Genre: ");
        ratingLabel = new JLabel("Rating: ");
        favouriteLabel = new JLabel("Favourite?: ");
        editName = new JTextField(10);
        editArtist = new JTextField(10);
        editYear = new JTextField(4);
        editMonth = new JTextField(2);
        editDay = new JTextField(2);
        editGenre = new JTextField(10);
        editRating = new JTextField(1);
        editListener = new EditListener();

        infoPaneButtonInit();
    }

    // MODIFIES: this
    // EFFECTS: creates and initializes buttons needed for infoPane
    private void infoPaneButtonInit() {
        editNameButton = new JButton("Edit");
        editArtistButton  = new JButton("Edit");
        editYearButton = new JButton("Edit");
        editMonthButton = new JButton("Edit");
        editDayButton = new JButton("Edit");
        editGenreButton = new JButton("Edit");
        editRatingButton  = new JButton("Edit");
        toggleFavouriteButton = new JButton("Toggle");

        createEditButton(editNameButton, "Name");
        createEditButton(editArtistButton, "Artist");
        createEditButton(editYearButton, "Year");
        createEditButton(editMonthButton, "Month");
        createEditButton(editDayButton, "Day");
        createEditButton(editGenreButton, "Genre");
        createEditButton(editRatingButton, "Rating");
        toggleFavouriteButton.setActionCommand("Favourite");
        toggleFavouriteButton.addActionListener(new ToggleListener());
        toggleFavouriteButton.setEnabled(true);
    }

    // MODIFIES: this
    // EFFECTS: creates button, assigns edit listener and given command
    private void createEditButton(JButton button, String command) {
        button.setActionCommand(command);
        button.addActionListener(editListener);
        button.setEnabled(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes buttons on button pane, sets up listeners, and places buttons on buttonPane
    public void setupButtonPane() {
        buttonPaneCompInit();

        addButtonPaneSaveLoad(saveButton);
        addButtonPaneSaveLoad(loadButton);
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        addButtonPaneSaveLoad(removeButton);
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        addButtonPaneNewAlbumField(nameTextLabel, albumName);
        addButtonPaneNewAlbumField(artistTextLabel, albumArtist);
        addButtonPaneNewAlbumField(yearTextLabel, albumYear);
        addButtonPaneNewAlbumField(monthTextLabel, albumMonth);
        addButtonPaneNewAlbumField(dayTextLabel, albumDay);
        addButtonPaneNewAlbumField(genreTextLabel, albumGenre);
        addButtonPaneSaveLoad(addButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    // MODIFIES: this
    // EFFECTS: places label and textfield on buttonPane
    private void addButtonPaneNewAlbumField(JLabel textLabel, JTextField textfield) {
        buttonPane.add(textLabel);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(textfield);
        buttonPane.add(Box.createHorizontalStrut(5));
    }

    // MODIFIES: this
    // EFFECTS: places button on buttonPane
    private void addButtonPaneSaveLoad(JButton button) {
        buttonPane.add(button);
        buttonPane.add(Box.createHorizontalStrut(5));
    }

    // MODIFIES: this
    // EFFECTS: initializes buttons on button pane, sets up listeners
    private void buttonPaneCompInit() {
        addButton = new JButton("Add");
        addListener = new AddListener(addButton);
        removeButton = new JButton("Remove");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        albumName = new JTextField(10);
        albumArtist = new JTextField(10);
        albumYear = new JTextField(4);
        albumMonth = new JTextField(2);
        albumDay = new JTextField(2);
        albumGenre = new JTextField(10);

        buttonPaneAddListeners();

        nameTextLabel = new JLabel("Name:");
        artistTextLabel = new JLabel("Artist:");
        yearTextLabel = new JLabel("Year:");
        monthTextLabel = new JLabel("Month:");
        dayTextLabel = new JLabel("Day:");
        genreTextLabel = new JLabel("Genre:");

        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
    }

    // MODIFIES: this
    // EFFECTS: sets up listeners for buttonpane buttons
    private void buttonPaneAddListeners() {
        addButton.setActionCommand("add");
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);
        removeButton.addActionListener(new RemoveListener());
        removeButton.setActionCommand("remove");
        saveButton.addActionListener(new SaveListener());
        saveButton.setActionCommand("save");
        loadButton.addActionListener(new LoadListener());
        loadButton.setActionCommand("load");
        albumName.addActionListener(addListener);
        albumArtist.addActionListener(addListener);
        albumYear.addActionListener(addListener);
        albumMonth.addActionListener(addListener);
        albumDay.addActionListener(addListener);
        albumGenre.addActionListener(addListener);
        albumName.getDocument().addDocumentListener(addListener);
        albumArtist.getDocument().addDocumentListener(addListener);
        albumYear.getDocument().addDocumentListener(addListener);
        albumMonth.getDocument().addDocumentListener(addListener);
        albumDay.getDocument().addDocumentListener(addListener);
        albumGenre.getDocument().addDocumentListener(addListener);
    }

    private class SaveListener implements ActionListener {
        // MODIFIES: this
        // EFFECTS: when save button pressed, takes all albums in listModel and adds to myCatalogue, and writes to json
        public void actionPerformed(ActionEvent e) {

//            for (int i = 0; i < albumList.getModel().getSize(); i++) {
//                myCatalogue.addAlbum(albumList.getModel().getElementAt(i));
//            }

            saveCatalogue();
        }
    }

    private class LoadListener implements ActionListener {
        // MODIFIES: this
        // EFFECTS: when load button pressed, erases all albums in listModel, loads all albums from json to catalogue,
        //          and adds loaded albums to listModel
        public void actionPerformed(ActionEvent e) {
            myCatalogue = new Catalogue("myCatalogue");
            listModel.removeAllElements();

            loadCatalogue();

            for (Album a : myCatalogue.getAlbums()) {
                listModel.addElement(a);
            }
        }
    }

    // EFFECTS: saves the workroom to file
    private void saveCatalogue() {
        try {
            jsonWriter.open();
            jsonWriter.write(myCatalogue);
            jsonWriter.close();
            System.out.println("Saved " + myCatalogue.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadCatalogue() {
        try {
            myCatalogue = jsonReader.read();
            System.out.println("Loaded " + myCatalogue.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    private class RemoveListener implements ActionListener {
        // MODIFIES: this
        // EFFECTS: if remove button is pressed, removes selected album from listModel
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            Album toBeRemoved = albumList.getSelectedValue();
            myCatalogue.removeAlbum(toBeRemoved.getName());
            int index = albumList.getSelectedIndex();
            listModel.remove(index);

            int size = listModel.getSize();

            if (size == 0) { //Nobody's left, disable firing.
                removeButton.setEnabled(false);
            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                albumList.setSelectedIndex(index);
                albumList.ensureIndexIsVisible(index);
            }
        }
    }

    private class EditListener implements ActionListener, DocumentListener {
        // MODIFIES: this
        // EFFECTS: if edit button is pressed, takes input from respective textField, and sets respective info for
        //          selected album and displays change on infoPane.
        @Override
        public void actionPerformed(ActionEvent e) {
            String str = e.getActionCommand();

            if (str == "Name") {
                editName();
            } else if (str == "Artist") {
                editArtist();
            } else if (str == "Year") {
                editYear();
            } else if (str == "Month") {
                editMonth();
            } else if (str == "Day") {
                editDay();
            } else if (str == "Genre") {
                editGenre();
            } else {
                editRating();
            }
        }

        // MODIFIES: this
        // EFFECTS: takes input from rating textField, and sets rating for selected album
        //          and displays change on infoPane.
        private void editRating() {
            albumList.getSelectedValue().rate(Integer.parseInt(editRating.getText()));
            selectedRating.setText(Integer.toString(albumList.getSelectedValue().getRating()));
            editRating.setText("");
        }

        // MODIFIES: this
        // EFFECTS: takes input from rating textField, and sets genre for selected album
        //          and displays change on infoPane.
        private void editGenre() {
            albumList.getSelectedValue().setGenre(editGenre.getText());
            selectedGenre.setText(albumList.getSelectedValue().getGenre());
            editGenre.setText("");
        }

        // MODIFIES: this
        // EFFECTS: takes input from rating textField, and sets day for selected album
        //          and displays change on infoPane.
        private void editDay() {
            albumList.getSelectedValue().setDate(albumList.getSelectedValue().getYear(),
                    albumList.getSelectedValue().getMonth(), Integer.parseInt(editDay.getText()));
            selectedDay.setText(Integer.toString(albumList.getSelectedValue().getDay()));
            editDay.setText("");
        }

        // MODIFIES: this
        // EFFECTS: takes input from rating textField, and sets month for selected album
        //          and displays change on infoPane.
        private void editMonth() {
            albumList.getSelectedValue().setDate(albumList.getSelectedValue().getYear(),
                    Integer.parseInt(editMonth.getText()), albumList.getSelectedValue().getDay());
            selectedMonth.setText(Integer.toString(albumList.getSelectedValue().getMonth()));
            editMonth.setText("");
        }

        // MODIFIES: this
        // EFFECTS: takes input from rating textField, and sets year for selected album
        //          and displays change on infoPane.
        private void editYear() {
            albumList.getSelectedValue().setDate(Integer.parseInt(editYear.getText()),
                    albumList.getSelectedValue().getMonth(), albumList.getSelectedValue().getDay());
            selectedYear.setText(Integer.toString(albumList.getSelectedValue().getYear()));
            editYear.setText("");
        }

        // MODIFIES: this
        // EFFECTS: takes input from rating textField, and sets artist for selected album
        //          and displays change on infoPane.
        private void editArtist() {
            albumList.getSelectedValue().setArtist(editArtist.getText());
            selectedArtist.setText(albumList.getSelectedValue().getArtist());
            editArtist.setText("");
        }

        // MODIFIES: this
        // EFFECTS: takes input from rating textField, and sets name for selected album
        //          and displays change on infoPane.
        private void editName() {
            albumList.getSelectedValue().setName(editName.getText());
            selectedName.setText(albumList.getSelectedValue().getName());
            editName.setText("");
        }

        @Override
        public void insertUpdate(DocumentEvent e) {

        }

        @Override
        public void removeUpdate(DocumentEvent e) {

        }

        @Override
        public void changedUpdate(DocumentEvent e) {

        }
    }

    private class ToggleListener implements ActionListener {
        // MODIFIES: this
        // EFFECTS: when toggle button is pressed, favouriteStatus of selected album is toggled, and image is displayed
        //          if currently is a favourite
        public void actionPerformed(ActionEvent e) {
            albumList.getSelectedValue().favourite();

            if (albumList.getSelectedValue().getFavouriteStatus()) {
                selectedFavourite.setText("Yes");
                selectedFavouriteIcon.setIcon(starIcon);
            } else {
                selectedFavourite.setText("No");
                selectedFavouriteIcon.setIcon(null);
                selectedFavouriteIcon.revalidate();
            }
        }
    }

    //This listener is shared by the text fields and the add button.
    private class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        // EFFECTS: sets given button as current button
        public AddListener(JButton button) {
            this.button = button;
        }

        // MODIFIES: this
        // EFFECTS: if all textfields are filled, will collect inputs and create new album, which is then added to
        //          the listModel
        public void actionPerformed(ActionEvent e) {
            String name = albumName.getText();
            String artist = albumArtist.getText();
            String year = albumYear.getText();
            String month = albumMonth.getText();
            String day = albumDay.getText();
            String genre = albumGenre.getText();
            Album newAlbum = new Album(name, artist, Integer.parseInt(year), Integer.parseInt(month),
                    Integer.parseInt(day), genre);

            int index = albumList.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            listModel.insertElementAt(newAlbum, index);
            myCatalogue.addAlbum(newAlbum);
            //Reset the text field.
            resetFields();

            //Select the new item and make it visible.
            albumList.setSelectedIndex(index);
            albumList.ensureIndexIsVisible(index);
        }

        // MODIFIES: this
        // EFFECTS: resets all textfields to empty
        private void resetFields() {
            albumName.requestFocusInWindow();
            albumName.setText("");
            albumArtist.requestFocusInWindow();
            albumArtist.setText("");
            albumYear.requestFocusInWindow();
            albumYear.setText("");
            albumMonth.requestFocusInWindow();
            albumMonth.setText("");
            albumDay.requestFocusInWindow();
            albumDay.setText("");
            albumGenre.requestFocusInWindow();
            albumGenre.setText("");
        }

        // EFFECTS: determines whether one of the textfields is empty or null
        public boolean isTextFieldEmpty() {
            return albumName.getText().equals(null) || albumArtist.getText().equals(null)
                    || albumYear.getText().equals(null) || albumMonth.getText().equals(null)
                    || albumDay.getText().equals(null) || albumGenre.getText().equals(null)
                    || albumName.getText().equals("") || albumArtist.getText().equals("")
                    || albumYear.getText().equals("") || albumMonth.getText().equals("")
                    || albumDay.getText().equals("") || albumGenre.getText().equals("");
        }

        //This method tests for string equality
//        protected boolean alreadyInList(String name) {
//            return listModel.contains(name);
//        }

        // MODIFIES: this
        // EFFECTS: if any of the textfields are empty, disables addButton, otherwise enables addButton
        public void insertUpdate(DocumentEvent e) {
            if (isTextFieldEmpty()) {
                button.setEnabled(false);
            } else {
                button.setEnabled(true);
            }
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        // MODIFIES: this
        // EFFECTS: disables button if textfields are empty
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    public static void createAndShowGUI() {
        //set up the window.
        JFrame frame = new JFrame("My Catalogue");
        frame.setSize(1000, 800);
        frame.addWindowListener(new CloseListener());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new CatalogueUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.setVisible(true);
    }
}
