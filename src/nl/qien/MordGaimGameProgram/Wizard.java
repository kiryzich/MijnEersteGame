package nl.qien.MordGaimGameProgram;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wizard extends GameObject {

    Handeler handeler;
    Game game;

    private BufferedImage[] wizard_image = new BufferedImage[3];

    Animation anim;

    public Wizard (int x, int y, ID id, Handeler handeler, Game game, SpriteSheet ss){
        super(x, y, id, ss);
        this.handeler = handeler;
        this.game = game;

        wizard_image[0] = ss.grabImage(1, 1, 32, 48);
        wizard_image[1] = ss.grabImage(2, 1, 32, 48);
        wizard_image[2] = ss.grabImage(3, 1, 32, 48);

        anim = new Animation(3, wizard_image[0], wizard_image[1], wizard_image[2]);
    }

    public void tick() {
        x += velX;
        y += velY;

        collision();

        //movement van je pupke
        if(handeler.isUp()) velY = -5;
        else if(!handeler.isDown()) velY = 0;

        if(handeler.isDown()) velY = 5;
        else if(!handeler.isUp()) velY = 0;

        if(handeler.isRight())  velX = 5;
        else if(!handeler.isLeft()) velX = 0;

        if(handeler.isLeft()) velX = -5;
        else if(!handeler.isRight()) velX = 0;

        anim.runAnimation();

    }

    private void collision() {
        for (int i = 0; i < handeler.object.size(); i++) {

            GameObject tempObject = handeler.object.get(i);

            if (tempObject.getId() == ID.Block) {

                if (getBounds().intersects(tempObject.getBounds())) {
                    x += velX * -1;
                    y += velY * -1;

                }

            }
            if (tempObject.getId() == ID.Crate) {

                if (getBounds().intersects(tempObject.getBounds())) {
                    game.ammo += 10;
                    handeler.removeObject(tempObject);

                }

                if (tempObject.getId() == ID.Enemy) {

                    if (getBounds().intersects(tempObject.getBounds())) {
                        game.hp--;

                    }

                }
            }

        }
    }


    public void render(Graphics g) {
        if(velX == 0 && velY == 0)
        g.drawImage(wizard_image[0], x, y, null);
        else
            anim.drawAnimation(g,x,y,0);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 32,48);
    }
}
