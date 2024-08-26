package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    BufferedImage seta, background;

    public int commandNum = 0;

    public UI(GamePanel gp){
        this.gp = gp;

        try{
            background = ImageIO.read(getClass().getResourceAsStream("/ui/menu.png"));
        }catch (IOException e){
            e.printStackTrace();
        }

        try {
            seta = ImageIO.read(getClass().getResourceAsStream("/ui/seta.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Font arial_40 = new Font ("Monospaced", Font.PLAIN, 80);

    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.white);

        //MENU
        if(gp.gameState == gp.titleState){
            drawTitleScreen();


        }
        //PAUSA
        if(gp.gameState == gp.playState){
            //fazer depois
        }if(gp.gameState == gp.pauseState){
            DrawPauseScreen();
        }
    }
    public void drawTitleScreen(){

        String text = "Need More Speed";
        int x = getXforCenteredText(text);
        int y = gp.D_H/4;


        x = getXforCenteredText(text);
        y += gp.D_W/8;


        g2.drawImage(background, 0, 0, gp.D_W, gp.D_H, null);
        if(commandNum == 0){

            g2.drawImage(seta, x , y+40, 100, 100, null);
        }



        x = getXforCenteredText(text);
        y += gp.D_W/10;

        if(commandNum == 1){
            g2.drawImage(seta, x +100, y+30, 100, 100, null);

        }

    }

    public void DrawPauseScreen(){

        Color cinza = new Color(49, 49, 49, 128);
        g2.setColor(cinza);
        g2.fillRect(0,0, gp.D_W, gp.D_H);
        g2.setColor(Color.white);
        String text = "PAUSED";
        int x = getXforCenteredText(text) ;
        int y = gp.D_H/2;

        g2.drawString(text, x,y);

    }

    public int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.D_W/2 - length/2;
        return x;
    }
}
