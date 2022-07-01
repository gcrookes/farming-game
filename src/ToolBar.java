import java.awt.*;
import java.awt.image.BufferedImage;

public class ToolBar extends GameObject{

    private BufferedImage hoeImage, seedImage;
    private int width, height, itemSelected = 0;

    public ToolBar() {
        super(0, 0);

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

    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(0,0,width,height);
    }
}
