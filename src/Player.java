import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Player extends GameObject{
    
    Random r = new Random();
    private static int WIDTH = 32;
    private static int HEIGHT = 64;
    Handler handler;

    private BufferedImage playerImage;

    public Player(ID id, Handler handler) {
        super(1000, 1000, id);
        this.handler = handler;

        velocity = 4;
        playerImage = Game.characterImages.grabImage(1,1,31,63);
    }

    @Override
    public void tick() {

        x += velX;
        y += velY;

        collision();

    }

    private void collision() {

    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.drawImage(playerImage, x, y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
}
