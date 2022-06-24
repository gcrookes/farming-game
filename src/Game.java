import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {
    public static final long serialVersionUID = 1550691097823471818L;
    public static boolean DEV_MODE = false;
    private Thread thread;
    private boolean running = false;
    private Handler handler;
    public static SpriteSheet backgroundImages, characterImages;
    private static BufferedImage background;
    private Window window;
    private Player player;
    private int frameRate;
    private double scale = 1.25;

    public Game() {

        handler = new Handler();
        this.addKeyListener(new KeyInput(handler, this));
//        this.addMouseListener(menu);

        BufferedImageLoader loader = new BufferedImageLoader();
        backgroundImages = new SpriteSheet(loader.loadImage("/Backgrounds.png"));
        characterImages = new SpriteSheet(loader.loadImage("/Characters.png"));
        background = backgroundImages.grabImage(1, 1, 4000 - 1, 2500 - 1);

        this.player = new Player(750, 550, ID.Player, handler);
        handler.addObject(player);

        handler.addObject(new Obstacle(0,  450, 4000,50,ID.Obstacle, handler));
        handler.addObject(new Obstacle(0, 2000, 4000,50,ID.Obstacle, handler));
        handler.addObject(new Obstacle(650, 0, 50,2500,ID.Obstacle, handler));
        handler.addObject(new Obstacle(3300,0, 50,2500,ID.Obstacle, handler));
        handler.addObject(new Obstacle(1755,1180, 2530 - 1755,1720 - 1180,ID.Obstacle, handler));

        window = new Window(800, 500, "Farming Game", this);

    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                tick();
                delta--;
            }

            if (running) {
                render();
            }

            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frameRate = frames;
                frames = 0;
            }
        }

    }

    /**
     * Method to render the game. Renders all the gameobjects as part of a bufferstrategy. Runs on every tick.
     */
    private void render() {
        // Create a buffer strategy and make graphics out of it
        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null) {
            // Create a three layer buffer on the first render
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        ((Graphics2D) g).scale(scale,scale);

        // Make the player always render at the center of the screen. To achieve this without messing up the
        // x and y axis of the graphic. Offset the graphic left and up by the x position of the player - the
        // distance between the corner of the window and the corner of the player
        Rectangle playerBox = this.player.getBounds();
        int xOffset = (int) (playerBox.getX() - (window.width() - playerBox.getWidth()*scale) / 2 / scale);
        int yOffset = (int) (playerBox.getY() - ((window.height() - playerBox.getHeight()*scale) / 2 - 20) / scale);
        g.translate(-xOffset, -yOffset);

        // Draw the background image on the frame
        g.drawImage(background, 0, 0, null);

        // Render all the game objects
        handler.render(g);

        if (DEV_MODE) {
            // Create a display in the top left corner of the screen with usefull stats
            ((Graphics2D) g).scale(1/scale, 1/scale);
            BufferedImage display = HUD.DevelopmentWindow(frameRate, player.getX(), player.getY());
            g.drawImage(display, (int) (xOffset * scale + 20), (int) (yOffset * scale + 20), null);
        }

        // Get rid of the graphic and show the buffer
        g.dispose();
        bs.show();
    }

    private void tick() {
        handler.tick();
    }

    public static void main(String[] args) {
        new Game();
    }

    public void increaseScale() {
        scale += 0.25;
        scale = Calcs.clamp(scale, 0.75, 3);
    }

    public void decreaseScale() {
        scale -= 0.25;
        scale = Calcs.clamp(scale, 0.75, 3);
    }
}