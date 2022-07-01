import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {
    public static final long serialVersionUID = 1550691097823471818L;
    public static boolean DEV_MODE = true;
    private Thread thread;
    private boolean running = false;
    private Handler handler;
    public static SpriteSheet characterImages;
    private Window window;
    private Player player;
    public int frameRate;
    private MouseInput mInput;
    private Display display;

    public Game() {

        mInput = new MouseInput();
        this.addMouseListener(mInput);

        BufferedImageLoader loader = new BufferedImageLoader();
        characterImages = new SpriteSheet(loader.loadImage("/Characters.png"));

        handler = new Handler();

        window = new Window(800, 500, "Farming Game", this);
        player = new Player(750, 550, handler);
        display = new Display(window, player, handler);

        handler.addObject(player);

        this.addKeyListener(new KeyInput(handler, display));

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
        } catch (Exception e) {
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

        if (mInput.isClicked()) {
            display.mouseInput();

        }

        Graphics g = bs.getDrawGraphics();

        if (display != null) {
            display.render(g, frameRate);
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