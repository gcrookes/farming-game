package InputOutput;

import GameLogic.Game;
import GameObjects.GameObject;
import GameObjects.Obstacle;

import java.util.Iterator;
import java.util.LinkedList;
import java.awt.Graphics;

public class Handler {

    public LinkedList<GameObject> object = new LinkedList<GameObject>();
    
    public void tick(){
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            tempObject.tick();
        }
    }

    public void render(Graphics g){

        // Iterate over all elements in the linked list and render them
        Iterator it = object.iterator();
        while (it.hasNext()){

            GameObject tempObject = (GameObject) it.next();

            // Don't render obstacles unless DEV_MODE is true
            if (tempObject instanceof Obstacle && !Game.DEV_MODE) {
                continue;
            }

            tempObject.render(g);

        }
    }

    public void addObject(GameObject object) {
        this.object.add(object);
    }

    public void removeObject(GameObject object) {
        this.object.remove(object);
    }
}
