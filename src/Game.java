import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {
    public static final long serialVersionUID = 1550691097823471818L;
    public static boolean DEV_MODE = true;
    private Thread thread;
    private boolean running = false;
    private Handler handler;
    public static SpriteSheet backgroundImages, characterImages;
    private static BufferedImage background;
    private Window window;
    private Player player;
    private int frameRate;

    public Game() {

        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));
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

    public static int clamp( int var, int min, int max) {
        if (var >= max) {
            return max;
        }
        
        if (var <= min) {
            return min;
        }

        return var;
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

        // Make the player always render at the center of the screen. To achieve this without messing up the
        // x and y axis of the graphic. Offset the graphic left and up by the x position of the player - the
        // distance between the corner of the window and the corner of the player
        Rectangle playerBox = this.player.getBounds();
        int xOffset = (int) (playerBox.getX() - (window.width() - playerBox.getWidth()) / 2);
        int yOffset = (int) (playerBox.getY() - (window.height() - playerBox.getHeight()) / 2 - 20);
        g.translate(-xOffset, -yOffset);

        // Draw the background image on the frame
        g.drawImage(background, 0, 0, null);

        // Render all the game objects
        handler.render(g);

        if (DEV_MODE) {
            // Create a FPS counter in the top left corner
            g.setColor(Color.white);
            g.fillRect(xOffset + 15, yOffset + 6, 60, 60);
            g.setColor(Color.BLACK);
            g.drawString("FPS: " + frameRate,   xOffset + 20, yOffset + 20);
            g.drawString("X: " + player.getX(), xOffset + 20, yOffset + 40);
            g.drawString("Y: " + player.getY(), xOffset + 20, yOffset + 60);

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
}