package othello.gui;

import othello.model.OthelloGame;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;

/**
 * A Swing frontend for {@link OthelloGame Othello}.
 */
public class OthelloUI {
    /**
     * The model (the game that is currently played).
     */
    private final OthelloGame game;

    /**
     * The window frame object.
     */
    private final JFrame frame;

    public OthelloUI() {
        game = new OthelloGame();

        frame = new JFrame("Othello");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setupMenus();
        setupContent();

        frame.setLocation(200, 100);
        frame.pack();
        frame.setVisible(true);
    }

    void setupMenus() {
        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);
        JMenu fileMenu = new JMenu("File");
        menubar.add(fileMenu);
        JMenuItem exitMI = new JMenuItem("Exit");
        exitMI.addActionListener(e -> frame.dispose());
        fileMenu.add(exitMI);
    }

    void setupContent() {
        JPanel contentPane = new JPanel(new BorderLayout());
        frame.setContentPane(contentPane);

        OthelloBoardPanel othelloPanel = new OthelloBoardPanel(game);
        contentPane.add(othelloPanel, BorderLayout.CENTER);

        JLabel statusLabel = new JLabel("State is " + game.getState());
        statusLabel.setBorder(BorderFactory.createTitledBorder("State"));
        contentPane.add(statusLabel, BorderLayout.SOUTH);

        // DONE: react to game events by updating the text in the statusLabel
        this.game.addGameListener((evt) -> statusLabel.setText("State is " + this.game.getState()));
    }
}