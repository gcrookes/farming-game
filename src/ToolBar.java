import java.awt.*;
import java.awt.image.BufferedImage;

public class ToolBar {

    private BufferedImage hoeImage, seedImage;
    private int width, height;

    public ToolBar() {

        hoeImage = Game.characterImages.grabImage(0,3, 32,32);
        seedImage = Game.characterImages.grabImage(1,3,32,32);

    }

    public BufferedImage toolBarDisplay() {

        width = 360;
        height = 50;

        BufferedImage display = new BufferedImage(width,height,1);
        Graphics g = display.getGraphics();

        g.fillRect(0,0, width, height);
        g.drawRect(0, 0, width, height);
        g.drawImage(hoeImage, 12, 12, null);
        g.drawImage(seedImage, 50, 12, null);

        g.dispose();
        return display;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
