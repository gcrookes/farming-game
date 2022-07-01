import java.awt.*;
import java.awt.image.BufferedImage;

public class Background {

    private BufferedImage activeBackground, farmBackground;
    private static SpriteSheet backgroundImages;
    private Handler handler;
    private int[][] fieldState;

    public Background(String path, Handler handler) {
        this.handler = handler;

        BufferedImageLoader loader = new BufferedImageLoader();
        backgroundImages = new SpriteSheet(loader.loadImage(path));
        activeBackground = createFarmBackground();
        fieldState = new int[activeBackground.getWidth()/32+1][activeBackground.getHeight()/32+1];
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

    public void paintTile(Rectangle player, BufferedImage image){
        Graphics g = activeBackground.getGraphics();
        int x = (int) ((player.getX() + player.getWidth() / 2) / 32) * 32;
        int y = (int) ((player.getY() + player.getHeight()) / 32) * 32;
        g.drawImage(image, x, y, null);
        g.dispose();
    }

    public void render(Graphics g) {
        g.drawImage(activeBackground, 0 , 0, null);
    }

    public int tileStatus(int x, int y) {
        int[] tile = getTile(x,y);
        return fieldState[tile[0]][tile[1]];
    }

    public void setStatus(int x, int y, int status){
        int[] tile = getTile(x,y);
        fieldState[tile[0]][tile[1]] = status;
    }

    private int[] getTile(int x, int y) {
        int[] ints = {x / 32 - 1, y / 32 - 1};
        return ints;
    }

}
