package main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    BufferedImage seta;

    public int commandNum = 0;

    public UI(GamePanel gp){
        this.gp = gp;

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

        //BACKGROUND
        Color azulEscuro = new Color(0, 35, 91);
        g2.setColor(azulEscuro);
        g2.fillRect(0,0, gp.D_W, gp.D_H);


        //TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 78F));
        String text = "Need More Speed";
        int x = getXforCenteredText(text);
        int y = gp.D_H/4;


        //SOMBRA
        g2.setColor(Color.black);
        g2.drawString(text, x+5, y+5 );

// cor principal
        Color vermelho = new Color(226, 24, 24);
        g2.setColor(vermelho);
        g2.drawString(text, x,y);

        //MENU

        text = "JOGAR";
        x = getXforCenteredText(text);
        y += gp.D_W/8;
        g2.setColor(Color.black);
        g2.drawString(text, x+5, y+5 );
        g2.setColor(vermelho);
        g2.drawString(text, x,y);
        if(commandNum == 0){
            //g2.drawString(">", x - 100, y);

            g2.drawImage(seta, x -180, y-75, 100, 100, null);
        }



        text = "SAIR";
        x = getXforCenteredText(text);
        y += gp.D_W/10;
        g2.setColor(Color.black);
        g2.drawString(text, x+5, y+5 );
        g2.setColor(vermelho);
        g2.drawString(text, x,y);
        if(commandNum == 1){
           // g2.drawString(">", x - 100, y);
            g2.drawImage(seta, x - 125, y-75, 100, 100, null);

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
