package nl.qien.MordGaimGameProgram;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1l;

    private boolean isRunning = false;
    private Thread thread;
    private Handeler handeler;
    private Camera camera;
    private SpriteSheet ss;

    private BufferedImage level = null;
    private BufferedImage sprie_sheet = null;
    private BufferedImage floor = null;

    public int ammo = 100;
    public int hp = 100;

    public Game() {
        new Window(1000, 563, "Battlegrond", this);
        start();

        handeler = new Handeler();
        camera = new Camera(0, 0);
        this.addKeyListener(new KeyInput(handeler));

        BufferedImageLoader loader = new BufferedImageLoader();
        level = loader.loadImage("/Wizard_level.png");
        sprie_sheet = loader.loadImage("/sprite_sheet.png");

        ss = new SpriteSheet(sprie_sheet);

        floor = ss.grabImage(4, 2, 32, 32);

        this.addMouseListener(new MouseInput(handeler, camera, this, ss));

        loadLevel(level);
    }

    private void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    private void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        this.requestFocus();
        long lasTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lasTime) / ns;
            lasTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
            }

        }
        stop();
    }

    public void tick(){

        for(int i = 0; i < handeler.object.size(); i++)
            if(handeler.object.get(i).getId() == ID.Player){
            camera.tick(handeler.object.get(i));
            }
    handeler.tick();
    }

    public void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs ==  null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        ///////drawboard voor de objecten

        g2d.translate(-camera.getX(), -camera.getY());

        for(int xx = 0; xx < 30*72; xx+=32){
            for(int yy = 0; yy < 30*72; yy+=32 ){
                g.drawImage(floor, xx, yy, null);
            }
        }

        handeler.render(g);

        g2d.translate(camera.getX(), camera.getY());

        g.setColor(Color.RED);
        g.fillRect(5,5,200, 32);
        g.setColor(Color.GREEN);
        g.fillRect(5,5,hp*2, 32);
        g.setColor(Color.BLACK);
        g.drawRect(5,5,200, 32);

        g.setColor(Color.WHITE);
        g.drawString("Ammo: " + ammo, 5, 50);



        //////drawboard einde van de objecten
        g.dispose();
        bs.show();
    }

    //level laden lol
    private void loadLevel (BufferedImage image){
        int w = image.getWidth();
        int h = image.getHeight();

        for(int xx = 0; xx < w; xx++){
            for(int yy = 0; yy < h; yy++){
                int pixel = image.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if(red == 255)
                    handeler.addObject(new Block(xx*32, yy*32, ID.Block, ss));

                if(blue == 255 && green == 0)

                handeler.addObject(new Wizard(xx*32, yy*32, ID.Player, handeler, this, ss));

                if(green == 255 && blue == 0)
                    handeler.addObject(new Enemy(xx*32, yy*32, ID.Enemy, handeler, ss));

                if(green == 255 && blue == 255)
                    handeler.addObject(new Crate(xx*32, yy*32, ID.Crate, ss));
            }


        }
    }

    public static void main(String[] args){

        new Game();
    }

}
