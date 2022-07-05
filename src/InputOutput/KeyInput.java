package InputOutput;

import GameObjects.GameObject;
import GameObjects.Player;
import Rendering.Display;
import Rendering.ToolBar;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    private final Display display;
    private Handler handler;
    private boolean[] keyDown = new boolean[4];

    public KeyInput(Handler handler, Display display) {
        this.handler = handler;
        this.display = display;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_EQUALS) display.increaseScale();
        if (key == KeyEvent.VK_MINUS) display.decreaseScale();

        for (int i = 0; i < handler.object.size(); i++) {

            GameObject tempObject = handler.object.get(i);

            if (tempObject instanceof Player) {
                if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {tempObject.setVelY(-1); keyDown[0] = true;}
                if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {tempObject.setVelY(1); keyDown[1] = true;}
                if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {tempObject.setVelX(-1); keyDown[2] = true;}
                if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {tempObject.setVelX(1); keyDown[3] = true;}
                break;
            }

            if (tempObject instanceof ToolBar) {
                if (key >= KeyEvent.VK_1 && key <= KeyEvent.VK_9) ((ToolBar) tempObject).setItem(key - KeyEvent.VK_1);
            }

        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0; i < handler.object.size(); i++) {

            GameObject tempObject = handler.object.get(i);

            if (tempObject instanceof Player) {
                
                if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) keyDown[0] = false;
                if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) keyDown[1] = false;
                if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) keyDown[2] = false;
                if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) keyDown[3] = false;

                if (!keyDown[0] && !keyDown[1]) tempObject.setVelY(0);
                if (!keyDown[2] && !keyDown[3]) tempObject.setVelX(0);

                break;
            }
        }
    }

}
