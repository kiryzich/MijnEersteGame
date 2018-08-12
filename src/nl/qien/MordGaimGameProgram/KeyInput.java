package nl.qien.MordGaimGameProgram;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    Handeler handeler;

    public KeyInput(Handeler handeler){
        this.handeler = handeler;
    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();

        for(int i = 0; i < handeler.object.size(); i++){
            GameObject tempObject = handeler.object.get(i);

                if(tempObject.getId() == ID.Player){
                    if(key == KeyEvent.VK_W) handeler.setUp(true);
                    if(key == KeyEvent.VK_S) handeler.setDown(true);
                    if(key == KeyEvent.VK_A) handeler.setLeft(true);
                    if(key == KeyEvent.VK_D) handeler.setRight(true);
                }
        }


    }

    public void keyReleased(KeyEvent e){

        int key = e.getKeyCode();

        for(int i = 0; i < handeler.object.size(); i++){
            GameObject tempObject = handeler.object.get(i);

            if(tempObject.getId() == ID.Player){
                if(key == KeyEvent.VK_W) handeler.setUp(false);
                if(key == KeyEvent.VK_S) handeler.setDown(false);
                if(key == KeyEvent.VK_A) handeler.setLeft(false);
                if(key == KeyEvent.VK_D) handeler.setRight(false);
            }
        }


    }
}
