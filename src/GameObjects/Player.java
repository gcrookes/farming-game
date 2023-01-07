package GameObjects;

import GameLogic.Game;
import InputOutput.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Random;

public class Player extends GameObject {
    
    Random r = new Random();
    private static int WIDTH = 32;
    private static int HEIGHT = 64;
    Handler handler;

    private BufferedImage playerImage;

    public Player(int x, int y, Handler handler) {
        super(x, y);
        this.handler = handler;

        velocity = 4;
        playerImage = Game.characterImages.grabImage(0,0,32,64);
    }

    @Override
    public void tick() {

        x += velX;
        y += velY;

        collision();

    }

    private void collision() {

        Iterator it = handler.object.iterator();
        GameObject tempObj;

        while (it.hasNext()) {

            tempObj = (GameObject) it.next();

            if (!(tempObj.getBounds().intersects(getBounds()))) {
                continue;
            }

            if (tempObj instanceof Obstacle) {
                // Calculate the distance between each edge of the player object and the obstical
                int playerLeft = (int) (tempObj.getX() + tempObj.getBounds().getWidth() - x);
                int playerRight = x + WIDTH - tempObj.getX();
                int playerTop = (int) (tempObj.getY() + tempObj.getBounds().getHeight() - y);
                int playerBottom = y + HEIGHT - tempObj.getY();

                // Find out which integer is the smallest
                int minDistance = Math.min(Math.min(playerLeft, playerRight), Math.min(playerTop, playerBottom));

                // Offest the player by the smallest distance to get it to stop interfering
                if (minDistance == playerLeft)      x += playerLeft;
                if (minDistance == playerRight)     x -= playerRight;
                if (minDistance == playerTop)       y += playerTop;
                if (minDistance == playerBottom)    y -= playerBottom;
            }
        }
    }


    @Override
    public void render(Graphics g) {
        g.drawImage(playerImage, x, y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
}
