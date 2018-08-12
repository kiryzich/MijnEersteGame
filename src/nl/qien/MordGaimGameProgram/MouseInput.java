package nl.qien.MordGaimGameProgram;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {

    private Handeler handeler;
    private Camera camera;
    private Game game;
    private SpriteSheet ss;

    public MouseInput(Handeler handeler, Camera camera, Game game, SpriteSheet ss){
        this.handeler = handeler;
        this.camera = camera;
        this.game = game;
        this.ss = ss;
    }

    public void mousePressed(MouseEvent e){
        int mx = (int) (e.getX() + camera.getX());
        int my = (int) (e.getY() + camera.getY());

        for (int i = 0; i < handeler.object.size(); i++){
            GameObject tempObject = handeler.object.get(i);

            if(tempObject.getId() == ID.Player && game.ammo >= 1){
                handeler.addObject(new Bullet(tempObject.getX() +16, tempObject.getY() +24, ID.Bullet, handeler, mx, my, ss));
                game.ammo--;
            }
        }
    }
}
