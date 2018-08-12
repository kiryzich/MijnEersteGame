package nl.qien.MordGaimGameProgram;

import java.awt.*;

public class Bullet extends GameObject {

    private Handeler handeler;

    public Bullet(int x, int y, ID id, Handeler handeler, int mx, int my, SpriteSheet ss) {
        super(x, y, id, ss);
        this.handeler = handeler;

        velX = (mx - x) / 10;
        velY = (my - y) / 10;
    }

    public void tick() {
        x += velX;
        y += velY;

        for(int i = 0; i < handeler.object.size(); i++){
            GameObject tempObject = handeler.object.get(i);

            if(tempObject.getId() == ID.Block){
                if(getBounds().intersects(tempObject.getBounds())){
                    handeler.removeObject(this);
                }
            }
        }

    }


    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, 8, 8);
    }


    public Rectangle getBounds() {
        return new Rectangle(x, y , 8, 8);
    }
}
