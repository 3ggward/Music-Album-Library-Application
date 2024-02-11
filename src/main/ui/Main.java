package ui;

import static ui.CatalogueUI.createAndShowGUI;

public class Main {
    // EFFECTS: creates and shows this application's GUI.
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
