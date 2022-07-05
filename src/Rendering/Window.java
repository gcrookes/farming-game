package Rendering;

import GameLogic.Game;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Window extends Canvas {
    
    private static final long serialVersionUID = -240840600533728354L;
    private JFrame frame;

    public Window(int width, int height, String title, Game game) {

        frame = new JFrame(title);

        frame.setMinimumSize(new Dimension(width, height));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
        game.start();

    }

    public int width() {
        return frame.getWidth();
    }

    public int height() {
        return frame.getHeight();
    }

}
