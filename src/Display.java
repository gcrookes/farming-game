import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.image.BufferedImage;
import java.sql.SQLOutput;

public class Display implements Clickable{

    private Window window;
    private double scale = 1.25;
    private Background background;
    private BufferedImage hoedTileImage, plantedTileImage, displayImage;
    private ToolBar toolBar;
    private Handler obstacles = new Handler();
    private Player player;
    private String string;
    private Handler handler;

    public Display(Window window, Player player, Handler handler) {
        this.window = window;
        this.background = new Background("/Backgrounds.png", handler);
        this.toolBar = new ToolBar(this.window);
        this.player = player;
        this.hoedTileImage = Game.characterImages.grabImage(0,1,32,32);
        this.plantedTileImage = Game.characterImages.grabImage(1,1,32,32);
        this.string = " ";
        this.handler = handler;
        this.handler.addObject(this.toolBar);
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

        // Draw the toolbar on the screen
        toolBar.setOffset((int) (xOffset*scale), (int) (yOffset*scale));
        toolBar.render(g);

        // Create a display in the top left corner of the screen with usefull stats
        if (Game.DEV_MODE) {
            BufferedImage display = HUD.DevelopmentWindow(frameRate, player.getX(), player.getY());
            g.drawImage(display, (int) (xOffset * scale + 20), (int) (yOffset * scale + 20), null);
            obstacles.render(g);
        }
    }

    public void mouseInput() {
        background.paintTile(player.getBounds(), hoedTileImage);
    }

    public void increaseScale() {
        scale += 0.25;
        scale = Calcs.clamp(scale, 0.75, 3);
    }

    public void decreaseScale() {
        scale -= 0.25;
        scale = Calcs.clamp(scale, 0.75, 3);
    }

    @Override
    public void clicked(int xClick, int yClick) {
        Rectangle playerRect = player.getBounds();
        int px = playerRect.x;
        int py = playerRect.y;
        switch (toolBar.getTool()) {
            case 0:
                background.setStatus(px, py, 1);
                background.paintTile(playerRect, hoedTileImage);
                break;
            case 1:
                if (background.tileStatus(px,py) == 1) {
                    background.setStatus(px, py, 2);
                    background.paintTile(player.getBounds(), plantedTileImage);
                }
                break;
        }

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(0,0,window.width(),window.height());
    }

    public ToolBar getToolBar() {
        return toolBar;
    }

    public void tick() {
        toolBar.tick();
    }
}
