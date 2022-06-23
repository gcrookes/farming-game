import java.awt.*;

public abstract class GameObject {
    
    protected int x, y;
    protected ID id;
    protected int velX, velY;
    protected int velocity;


    public GameObject(int x, int y, ID id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract Rectangle getBounds();

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

    public ID getId() {
        return id;
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
