package GameItems;

import GameObjects.Crop;
import GameLogic.Game;
import java.awt.image.BufferedImage;

public class Seed extends GameItem{

    private Crop crop;
    private int id;
    public Seed(int id, BufferedImage image) {
        super(id, image);
        this.id = id;
        crop = (Crop) Game.allObjects[id];

    }

    public Crop getCrop() {
        System.out.println(id);
        System.out.println(crop instanceof Crop);
        return crop;
    }

}
