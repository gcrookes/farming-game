import java.awt.image.BufferedImage;

public class SpriteSheet {
    private BufferedImage sprite;

    public SpriteSheet(BufferedImage sprite) {
        this.sprite = sprite;
    }

    public BufferedImage grabImage(int col, int row, int width, int height) {
        BufferedImage image = sprite.getSubimage((row * 32 - 31), (col * 32 - 31), width, height);
        return image;
    }
}
