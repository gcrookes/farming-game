import java.awt.*;
import java.awt.image.BufferedImage;

public class HUD {

    /**
     * Method to create a basic HUD for debugging purposes that displays a framerate and the player cooridnates
     *
     * @param frameRate the frame rate the game is currently running at
     * @param x the x position of the player
     * @param y the y position of the player
     * @return a BufferedImage of the display to be placed on another graphic
     */
    public static BufferedImage DevelopmentWindow(int frameRate, int x, int y) {
        // Set a width and height to make the window
        int width = 70;
        int height = 60;

        // Create a display and graphic
        BufferedImage display = new BufferedImage(width,height,1);
        Graphics g = display.getGraphics();


        // Draw a white backgrounded box with a black frame
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, width-1, height-1);

        // Set the font and draw in the strings
        g.setFont(new Font("Arial", 1, 15));
        g.drawString("FPS: " + frameRate, 5, height / 3 - height / 12);
        g.drawString("X: " + x, 5, height * 2 / 3 - height / 12);
        g.drawString("Y: " + y, 5, height - height / 12);

        // Get rid of the graphic and return the image
        g.dispose();
        return display;
    }

}
