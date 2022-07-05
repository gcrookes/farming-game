package GameObjects;

import java.awt.*;

public abstract class GameObject {
    
    protected int x, y;
    protected int velX, velY;
    protected int velocity;


    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract Rectangle getBounds();

    public void clicked(int xClick, int yClick) {

    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setVelX(int direction) {
        this.velX = direction * this.velocity;
    }

    public void setVelY(int direction) {
        this.velY = direction * this.velocity;;
    }

    public int getVelX() {
        return velX;
    }

    public int getVelY() {
        return velY;
    }

}
