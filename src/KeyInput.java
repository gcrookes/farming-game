import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.SortedMap;

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
                if (key == KeyEvent.VK_UP) {tempObject.setVelY(-1); keyDown[0] = true;}
                if (key == KeyEvent.VK_DOWN) {tempObject.setVelY(1); keyDown[1] = true;}
                if (key == KeyEvent.VK_LEFT) {tempObject.setVelX(-1); keyDown[2] = true;}
                if (key == KeyEvent.VK_RIGHT) {tempObject.setVelX(1); keyDown[3] = true;}
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
                
                if (key == KeyEvent.VK_UP) keyDown[0] = false;
                if (key == KeyEvent.VK_DOWN) keyDown[1] = false;
                if (key == KeyEvent.VK_LEFT) keyDown[2] = false;
                if (key == KeyEvent.VK_RIGHT) keyDown[3] = false;

                if (!keyDown[0] && !keyDown[1]) tempObject.setVelY(0);
                if (!keyDown[2] && !keyDown[3]) tempObject.setVelX(0);

                break;
            }
        }
    }

}
