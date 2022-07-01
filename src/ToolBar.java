import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class ToolBar extends GameObject implements Clickable{

    private BufferedImage hoeImage, seedImage;
    private int width, height, x, y, xOffset, yOffset, itemSelected = 0;
    private MouseInput mInput;
    private Window window;

    public ToolBar(Window window) {
        super(0, 0);

        hoeImage = Game.characterImages.grabImage(0,3, 32,32);
        seedImage = Game.characterImages.grabImage(1,3,32,32);
        this.window = window;

        width = 360;
        height = 50;
    }

    public BufferedImage toolBarDisplay() {



        BufferedImage display = new BufferedImage(width,height,1);
        Graphics g = display.getGraphics();

        g.fillRect(0,0, width, height);
        g.drawRect(0, 0, width, height);
        g.drawImage(hoeImage, 12, 12, null);
        g.drawImage(seedImage, 50, 12, null);

        g.setColor(Color.RED);
        ((Graphics2D) g).setStroke(new BasicStroke(4));
        g.drawRect(38 * itemSelected + 10,10,36,36);

        g.dispose();
        return display;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void nextItem(){
        itemSelected += 1;
        if (itemSelected > 8) itemSelected = 0;
    }

    public void setItem(int item){
        itemSelected = item;
    }

    @Override
    public void tick() {
        this.x = (window.width()-width)/2;
        this.y = window.height()-height-50;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(toolBarDisplay(), x + xOffset, y + yOffset,null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,width,height);
    }

    public void setOffset(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    @Override
    public void clicked(int xClick, int yClick) {
        itemSelected = (xClick - x - 10) / 38;
    }

    public int getTool() {
        return itemSelected;
    }
}
