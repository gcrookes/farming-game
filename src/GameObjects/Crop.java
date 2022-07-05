package GameObjects;


import Rendering.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Crop extends GameObject{

    private int maxStages = 6;
    private BufferedImage[] plantedTileImages = new BufferedImage[maxStages];
    private int[] daysToMature;

    public Crop(SpriteSheet cropImages, int seedNumber, int col, int[] daysToMature) {
        super(0,0);

        for (int i = 0; i < maxStages; i++) {
            plantedTileImages[i] = cropImages.grabImage(i,col,32,32);
        }

        this.daysToMature = daysToMature;

    }

    public BufferedImage getImage(int days) {

        for (int i = daysToMature.length - 1; i >= 0; i--) {
            if (days >= daysToMature[i]) {
                return plantedTileImages[i];
            }
        }

        return null;

    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public Rectangle getBounds() {
        return null;
    }
}
