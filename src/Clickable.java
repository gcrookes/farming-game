import java.awt.*;

public interface Clickable {
    public abstract void clicked(int xClick, int yClick);
    public abstract Rectangle getBounds();
}
