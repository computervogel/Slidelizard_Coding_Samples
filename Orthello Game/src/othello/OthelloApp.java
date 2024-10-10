package othello;

import othello.gui.OthelloUI;

import javax.swing.SwingUtilities;

public class OthelloApp {
    public static void main(String[] args) {
        // start the UI in the AWT thread
        SwingUtilities.invokeLater(OthelloUI::new);
    }
}
