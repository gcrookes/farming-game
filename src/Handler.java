import java.util.LinkedList;
import java.awt.Graphics;

public class Handler {

    private Game game;
    LinkedList<GameObject> object = new LinkedList<GameObject>();


    public Handler(Game game) {
        this.game = game;
    }
    
    public void tick(){
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            tempObject.tick();
        }
    }

    public void render(Graphics g){
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);

            if (tempObject instanceof Obstacle && !Game.DEV_MODE) {
                continue;
            }

            if (tempObject.getBounds().intersects(game.getWindow())) {
                tempObject.render(g);
            }
        }
    }

    public void addObject(GameObject object) {
        this.object.add(object);
    }

    public void removeObject(GameObject object) {
        this.object.remove(object);
    }
}
