package UI;

import main.GamePanel;

import java.awt.*;

public class GenericUI{
    GamePanel gp;
    Graphics2D g2;

    public GenericUI(GamePanel gp) {
        this.gp = gp;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setColor(Color.white);
        if(gp.gameState == gp.playState){
            //fazer depois
        }
    }
}
