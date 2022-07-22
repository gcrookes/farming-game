package Rendering;

import GameItems.GameItem;
import GameItems.Seed;
import GameLogic.Game;
import GameObjects.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ToolBar extends GameObject implements Clickable {

    private BufferedImage hoeImage, seedImage, canImage;
    private int width, height, x, y, xOffset, yOffset, itemSelected = 0;
    private Window window;
    private GameItem[] items = new GameItem[9];

    public ToolBar(Window window) {
        super(0, 0);
        this.window = window;


        hoeImage = Game.characterImages.grabImage(0,3, 32,32);
        seedImage = Game.characterImages.grabImage(1,3,32,32);
        canImage = Game.characterImages.grabImage(2,3,32,32);
        BufferedImage seed1 = Game.cropImages.grabImage(0,0,32,32);
        BufferedImage seed2 = Game.cropImages.grabImage(0,1,32,32);
        BufferedImage seed3 = Game.cropImages.grabImage(0,2,32,32);
        BufferedImage seed4 = Game.cropImages.grabImage(0,3,32,32);

        items[0] = new GameItem(0, hoeImage);
        items[1] = new Seed(6, seedImage);
        items[2] = new GameItem(2, canImage);
        items[3] = new Seed(7, seed1);
        items[4] = new Seed(8, seed2);
        items[5] = new Seed(9, seed3);
        items[6] = new Seed(10, seed4);

        width = 360;
        height = 50;
    }

    public BufferedImage toolBarDisplay() {

        BufferedImage display = new BufferedImage(width,height,1);
        Graphics g = display.getGraphics();

        g.fillRect(0,0, width, height);
        g.drawRect(0, 0, width, height);
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) continue;
            g.drawImage(items[i].getImage(), 12 + 38*i, 12, null);
        }

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

    public GameItem getTool() {
        return items[itemSelected];
    }
}
