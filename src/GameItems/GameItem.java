package GameItems;

import java.awt.image.BufferedImage;

public class GameItem {

    private int id, quantity = 1;
    private BufferedImage image;

    public GameItem (int id, BufferedImage image) {
        this.id = id;
        this.image = image;
    }

    public int getID() {
        return id;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getQty() {
        return quantity;
    }

    public void stack(GameItem item) {

        if (item.getID() != id) {
            throw new RuntimeException("Cannot stack items with different id's");
        }

        quantity += item.getQty();

    }
}
