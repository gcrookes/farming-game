package Rendering;

import GameItems.GameItem;
import GameItems.Seed;
import GameLogic.Game;
import GameObjects.Crop;
import GameObjects.Obstacle;
import Rendering.ImageImport.BufferedImageLoader;
import Rendering.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import InputOutput.Handler;

public class Background {

    private BufferedImage activeBackground, farmBackground;
    private static SpriteSheet backgroundImages;
    private Handler handler;
    private int[][] fieldState;
    private int tileWidth = 32;
    private BufferedImage[][] storedTiles;
    private Crop[][] cropsPlanted;
    private BufferedImage hoedTileImage;


    public Background(String path, Handler handler) {
        this.handler = handler;

        BufferedImageLoader loader = new BufferedImageLoader();
        backgroundImages = new SpriteSheet(loader.loadImage(path));
        activeBackground = createFarmBackground();
        storedTiles = new BufferedImage[activeBackground.getWidth()/tileWidth + 1][activeBackground.getHeight()/tileWidth + 1];
        fieldState = new int[activeBackground.getWidth()/tileWidth+1][activeBackground.getHeight()/tileWidth+1];
        cropsPlanted = new Crop[activeBackground.getWidth()/tileWidth+1][activeBackground.getHeight()/tileWidth+1];

        this.hoedTileImage = Game.characterImages.grabImage(0,1,32,32);
    }

    public BufferedImage createFarmBackground() {
        farmBackground = backgroundImages.grabImage(0, 0, 4000 - 1, 2500 - 1);

        handler.addObject(new Obstacle(0,  418, 4000,50, handler));
        handler.addObject(new Obstacle(0, 2000, 4000,50, handler));
        handler.addObject(new Obstacle(650, 0, 50,2500, handler));
        handler.addObject(new Obstacle(3300,0, 50,2500, handler));
        handler.addObject(new Obstacle(1755,1180, 2530 - 1755,1720 - 1180, handler));

        return farmBackground;
    }

    public void setCrop(int px, int py, GameItem seed) {
        int[] tile = getTile(px,py);
//        cropsPlanted[tile[0]][tile[1]] = ((Seed) seed).getCrop();
    }

    public void updateTile(int px, int py, GameItem tool) {
        //tools: 0 = Hoe // 1 = seed, // 2 = watering can
        //States: 0 = un-tilled, 1 = hoed, 2+ = planted

        int state = tileState(px,py);

        if (state == 0 && tool.getID() == 0) {
            // Hoe the ground
            setState(px, py, state + 1);
        }

        if (state == 1 && tool instanceof Seed) {
            // Plant a seed
            setCrop(px, py, tool);
            setState(px, py, state + 1);
        }

        if (state >= 2 && tool.getID() == 2) {
            // Water the crop
            setState(px, py, state + 1);
        }

        paintTile(px, py, tileState(px,py));

    }

    public void paintTile(int px, int py, int state){
        Graphics g = activeBackground.getGraphics();

        int[] tile = getTile(px,py);
        BufferedImage image;

        if (state > 0) {
            g.drawImage(hoedTileImage, tile[0] * tileWidth, tile[1] * tileWidth - hoedTileImage.getHeight() + tileWidth, null);
        }

        if (state > 1) {
            Crop crop = cropsPlanted[tile[0]][tile[1]];
            image = crop.getImage(state);
            storedTiles[tile[0]][tile[1]] = image;
            g.drawImage(image, tile[0] * tileWidth, tile[1] * tileWidth - image.getHeight() + tileWidth, null);
        }

        g.dispose();
    }

    public void render(Graphics g) {
        g.drawImage(activeBackground, 0 , 0, null);
    }

    public void renderInFrontOfPlayer(Graphics g, int px, int py) {
        int[] tile = getTile(px,py);
        for (int col = tile[0] - 3; col <= tile[0] + 3; col++) {
            for (int row  = tile[1]; row <= tile[1] + 4; row++) {
                if (storedTiles[col][row] != null) {
                    g.drawImage(storedTiles[col][row], col * tileWidth, row * tileWidth - storedTiles[col][row].getHeight() + tileWidth, null);
                }
            }
        }

    }

    public int tileState(int x, int y) {
        int[] tile = getTile(x,y);
        return fieldState[tile[0]][tile[1]];
    }

    public void setState(int x, int y, int status){
        int[] tile = getTile(x,y);
        fieldState[tile[0]][tile[1]] = status;
    }

    private int[] getTile(int x, int y) {
        int[] ints = {x / tileWidth, y / tileWidth};
        return ints;
    }

}
