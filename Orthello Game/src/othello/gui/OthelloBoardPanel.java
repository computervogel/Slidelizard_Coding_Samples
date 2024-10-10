package othello.gui;

import othello.model.OthelloException;
import othello.model.OthelloGame;
import othello.model.Pos;
import othello.model.Stone;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The board of {@link OthelloGame Othello} consists of 8x8 cells
 * where players alternate in playing stones..
 */
public class OthelloBoardPanel extends JPanel {
    /**
     * The game that is played.
     */
    private final OthelloGame game;
    private final Map<Pos, JButton> buttons;

    public OthelloBoardPanel(OthelloGame game) {
        this.game = game;

        // DONE: add grid layout
        setLayout(new GridLayout(8, 8));

        // DONE: add grid of buttons
        buttons = new HashMap<>();

        // DONE: add listener to game that updates button at the affected position
        Pos[] pos = Pos.ALL;

        for (Pos p : pos) {
            JButton button = createButton(p);
            button.addActionListener((e) -> {
                try {
                    game.setNextStone(p);
                } catch (OthelloException oExp) {
                    reportInvalidMove(oExp.getStone(), oExp.getPos());
                }

            });
            add(button);
            buttons.put(p, button);
        }

        game.addGameListener((e) -> {
            Pos p = e.getPos();
            JButton btn = buttons.get(p);
            setState(btn, game.getStone(p));
        });
    }

    /**
     * Helper method that shows a dialog window.
     * The dialog shows a warning due to an invalid move at the given position.
     *
     * @param stone The stone that was placed
     * @param pos   The invalid position
     */
    private void reportInvalidMove(Stone stone, Pos pos) {
        JOptionPane.showMessageDialog(this, "Invalid move %s on %s".formatted(stone, pos));
    }

    /**
     * Creates a new {@link JButton} and {@linkplain #setState(JButton, Stone) assigns a new state} based on the position it is placed on.
     *
     * @param pos The position for which the button should be created
     * @return a new {@link JButton} with a corresponding text and background
     */
    private JButton createButton(Pos pos) {
        JButton btn = new JButton();
        setState(btn, game.getStone(pos));
        return btn;
    }

    /**
     * Sets the text and background of the given button based on the given {@link Stone}.
     *
     * @param btn   The button that should be modified
     * @param stone The stone that determines the state
     */
    private void setState(JButton btn, Stone stone) {
        switch (stone) {
            case FREE -> {
                btn.setText("  ");
                btn.setBackground(Color.LIGHT_GRAY);
            }
            case BLACK -> {
                btn.setText("B");
                btn.setForeground(Color.WHITE);
                btn.setBackground(Color.BLACK);
            }
            case WHITE -> {
                btn.setText("W");
                btn.setForeground(Color.BLACK);
                btn.setBackground(Color.WHITE);
            }
        }
    }

}
