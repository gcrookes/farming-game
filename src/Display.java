import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.image.BufferedImage;
import java.sql.SQLOutput;

public class Display {

    private Window window;
    private double scale = 1.25;
    private Background background;
    private BufferedImage tileImage, displayImage;
    private ToolBar toolBar;
    private Handler obstacles = new Handler();
    private Player player;
    private String string;
    private Handler handler;

    public Display(Window window, Player player, Handler handler) {
        this.window = window;
        this.background = new Background("/Backgrounds.png", handler);
        this.toolBar = new ToolBar();
        this.player = player;
        this.tileImage = Game.characterImages.grabImage(0,1,32,32);
        this.string = " ";
        this.handler = handler;
    }

    /**
     * Method to render the game image on each tick. Calculates the offset to center the player, then offsets and scales the window and draws all objects
     * @param g: graphics object to paint the pixels on
     * @param frameRate: Framerate the game is running at for the DEV_MODE
     */
    public void render(Graphics g, int frameRate) {

        // Make the player always render at the center of the screen. To achieve this without messing up the
        // x and y axis of the graphic. Offset the graphic left and up by the x position of the player - the
        // distance between the corner of the window and the corner of the player
        Rectangle playerBox = player.getBounds();
        int xOffset = (int) (playerBox.getX() - (window.width() / 2.0 - playerBox.getWidth() * scale / 2.0)  / scale);
        int yOffset = (int) (playerBox.getY() - ((window.height() - playerBox.getHeight()*scale) / 2 - 20) / scale);

        // Scale up the graphics based on the game scale and then offset the plane by the offset
        ((Graphics2D) g).scale(scale,scale);
        g.translate(-xOffset, -yOffset);

        // Render the background and player
        background.render(g);
        player.render(g);
        handler.render(g);

        // Reset the scale to draw UI elements
        ((Graphics2D) g).scale(1/scale, 1/scale);

        // Calculate the offset for the toolbar and draw it
        int toolBarX = (int) (xOffset*scale + (window.width()-toolBar.getWidth())/2);
        int toolBarY = (int) (yOffset*scale + window.height()-toolBar.getHeight()-50);
        g.drawImage(toolBar.toolBarDisplay(), toolBarX, toolBarY,null);

        // Create a display in the top left corner of the screen with usefull stats
        if (Game.DEV_MODE) {
            BufferedImage display = HUD.DevelopmentWindow(frameRate, player.getX(), player.getY());
            g.drawImage(display, (int) (xOffset * scale + 20), (int) (yOffset * scale + 20), null);
            obstacles.render(g);
        }

    }

    public void mouseInput() {
        background.paintTile(player.getBounds(), tileImage);
    }

    public void increaseScale() {
        scale += 0.25;
        scale = Calcs.clamp(scale, 0.75, 3);
    }

    public void decreaseScale() {
        scale -= 0.25;
        scale = Calcs.clamp(scale, 0.75, 3);
    }
}
