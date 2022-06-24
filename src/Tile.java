import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile extends GameObject{

    private BufferedImage tileImage;

    public Tile(int x, int y) {
        super(x, y);

        tileImage = Game.characterImages.grabImage(1,2,32,32);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(tileImage, x, y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32,32);
    }
}
