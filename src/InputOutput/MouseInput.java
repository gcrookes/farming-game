package InputOutput;

import Rendering.Clickable;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.LinkedList;

public class MouseInput extends MouseAdapter {

    int xClick;
    int yClick;
    boolean clicked;
    LinkedList<Clickable> objects = new LinkedList<Clickable>();


    public void mousePressed(MouseEvent e) {

        xClick = e.getX();
        yClick = e.getY();

    }

    public void mouseReleased(MouseEvent e) {

        Iterator it = objects.iterator();
        Clickable obj;

        while (it.hasNext()){

            obj = (Clickable) it.next();

            if (mouseOver(obj.getBounds())) {
                obj.clicked(xClick, yClick);
            }

        }


    }

    private boolean mouseOver(int x, int y, int width, int height) {
        boolean xIntersection = xClick >= x && xClick <= x + width;
        boolean yIntersection = yClick >= y && yClick <= y + height;

        return xIntersection && yIntersection;
    }

    private boolean mouseOver(Rectangle rect) {
        boolean xIntersection = xClick >= rect.x && xClick <= rect.x + rect.width;
        boolean yIntersection = yClick >= rect.y && yClick <= rect.y + rect.height;

        return xIntersection && yIntersection;
    }

    public boolean isClicked() {
        boolean click = clicked;
        clicked = false;
        return click;
    }

    public int[] clickPosition() {
        return new int[]{xClick, yClick};
    }

    public void addClickable(Clickable obj) {
        objects.add(obj);
    }

}
