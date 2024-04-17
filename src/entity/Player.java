package entity;

import main.GamePanel;
import main.KeyHandler;
import java.awt.*;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public  Player(GamePanel gp, KeyHandler keyH){
        this.gp=gp;
        this.keyH=keyH;

        setDefaultValues();
    }

    public void setDefaultValues(){
        x = 499;
        y = 554;
        aderencia = 4;
        aceleracao = 1;
    }
    public void update(){
        if (keyH.upPressed){
            //y -= speed;
        } if (keyH.downPressed) {
            //y += speed;
        } if (keyH.leftPressed) {
            x -= aderencia;
        } if (keyH.rightPressed) {
            x += aderencia;
        }


        x = Math.max(40, Math.min(1208 - 330, x));
        y = Math.max(0, Math.min(720 - 150, y));
    }
    public void draw(Graphics2D g2){
        g2.setColor(Color.RED);
        g2.fillRect(x,y, 290, 150);
    }
}
