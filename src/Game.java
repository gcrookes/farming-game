import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Game extends Canvas implements Runnable {
    public static final long serialVersionUID = 1550691097823471818L;
    private Thread thread;
    private boolean running = false;
    private Handler handler;
    public static SpriteSheet backgroundImages, characterImages;
    private static BufferedImage background;
    private Window window;
    private Player player;

    public Game() {

        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));
//        this.addMouseListener(menu);

        BufferedImageLoader loader = new BufferedImageLoader();
        backgroundImages = new SpriteSheet(loader.loadImage("/Backgrounds.png"));
        characterImages = new SpriteSheet(loader.loadImage("/Characters.png"));
        background = backgroundImages.grabImage(1, 1, 4000 - 1, 2500 - 1);

        this.player = new Player(ID.Player, handler);
        handler.addObject(player);

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
                frames = 0;
            }
        }

    }

    private void render() {

        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        Rectangle player = this.player.getBounds();

        int playerXOffset = (int) (window.width() - player.getWidth()) / 2;
        int playerYOffset = (int) (window.height() - player.getHeight()) / 2 - 20;

        int playerX = (int) player.getX();
        int playerY = (int) player.getY();

        g.drawImage(background, playerXOffset - playerX, playerYOffset - playerY, null);

        this.player.render(g, playerXOffset, playerYOffset);


//        handler.render(g);

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