package GameLogic;

import GameItems.GameItem;
import GameObjects.Crop;
import GameObjects.GameObject;
import GameObjects.Player;
import InputOutput.KeyInput;
import InputOutput.MouseInput;
import Rendering.ImageImport.BufferedImageLoader;
import Rendering.Display;
import Rendering.SpriteSheet;
import Rendering.Window;
import InputOutput.Handler;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Game extends Canvas implements Runnable {
    public static final long serialVersionUID = 1550691097823471818L;
    public static boolean DEV_MODE = true;
    private Thread thread;
    private boolean running = false;
    private final Handler handler;
    public static SpriteSheet characterImages, cropImages;
    private final Window window;
    private final Player player;
    public int frameRate;
    private final MouseInput mInput;
    private final Display display;
    public static final GameObject[] allObjects = new GameObject[400];

    public Game() {

        mInput = new MouseInput();
        this.addMouseListener(mInput);

        BufferedImageLoader loader = new BufferedImageLoader();
        characterImages = new SpriteSheet(loader.loadImage("/Characters.png"));
        cropImages = new SpriteSheet(loader.loadImage("/Crops.png"));

        loadCrops(cropImages, "src/GameLogic/Crops.txt");

        handler = new Handler();

        window = new Window(800, 500, "Farming Game", this);
        player = new Player(750, 550, handler);
        display = new Display(window, player, handler);

        handler.addObject(player);
        mInput.addClickable(display);
        mInput.addClickable(display.getToolBar());

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
                frameRate = frames;
                frames = 0;
            }
        }

    }

    /**
     * Method to render the game. Renders all the game objects as part of a bufferstrategy. Runs on every tick.
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

        if (display != null) {
            display.render(g, frameRate);
        }

        // Get rid of the graphic and show the buffer
        g.dispose();
        bs.show();
    }

    private void tick() {
        if (display != null) display.tick();
        handler.tick();
    }

    public static void main(String[] args) {
        new Game();
    }

    public void loadCrops(SpriteSheet sprites, String filePath) {
        Scanner sc = null;

        try {
            sc = new Scanner(new File(filePath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String[] line;
        //0|1  |2    |3    |4           |5   |6      |7
        // |id |Type |Name |Sprite File |Row |Column |Days To Mature
        sc.nextLine();

        int id, row, col;

        while (sc.hasNext()) {

            line = sc.nextLine().split("[|]",-1);

            id = Integer.parseInt(line[1].trim());
            row = Integer.parseInt(line[5].trim());
            col = Integer.parseInt(line[6].trim());

            int[] days = Calcs.stringToIntArray(line[7],",");

            allObjects[id] = new Crop(sprites,id,col,days);

        }
    }

}