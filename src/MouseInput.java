import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {

    int xClick;
    int yClick;
    boolean clicked;

    public void mousePressed(MouseEvent e) {

        xClick = e.getX();
        yClick = e.getY();
        System.out.println("Clicked");

    }

    public void mouseReleased(MouseEvent e) {

        clicked = true;
//        if (mouseOver(10,10,90,40)) {
//            System.out.println("Do Something");
//        }

    }

    private boolean mouseOver(int x, int y, int width, int height) {
        boolean xIntersection = xClick >= x && xClick <= x + width;
        boolean yIntersection = yClick >= y && yClick <= y + height;

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

}
