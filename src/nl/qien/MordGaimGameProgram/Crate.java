package nl.qien.MordGaimGameProgram;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Crate extends GameObject {

    private BufferedImage crate_iamge;

    public Crate(int x, int y, ID id, SpriteSheet ss) {
        super(x, y, id, ss);

        crate_iamge = ss.grabImage(6, 2, 32, 32);
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.drawImage(crate_iamge, x, y, null);

    }

    public Rectangle getBounds() {
        return new Rectangle(x, y ,32, 32);
    }
}
