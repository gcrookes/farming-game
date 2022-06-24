import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Obstacle extends GameObject{

    private final int width;
    private final int height;
    private Polygon shape;
    Handler handler;

    private BufferedImage playerImage;

    public Obstacle(int x, int y, int width, int height,  ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;
        this.width = width;
        this.height = height;

        velocity = 10;
        playerImage = Game.characterImages.grabImage(2,2,31,63);
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;
    }

    @Override
    public void render(Graphics g) {
        if (Game.DEV_MODE) {
            g.setColor(Color.RED);
            g.drawRect(x, y, width, height);
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public Polygon getShape() {
        return shape;
    }
}
