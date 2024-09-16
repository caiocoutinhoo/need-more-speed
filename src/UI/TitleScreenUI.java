package UI;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class TitleScreenUI extends UI {
    BufferedImage seta;
    Graphics2D g2;
    public TitleScreenUI(GamePanel gp) {
        super(gp, "/ui/menu.png");
        try {
            seta = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/ui/seta.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void draw(Graphics2D g2){
        this.g2 = g2;
        String text = "Need More Speed";
        int x = getXforCenteredText(text);
        int y = gp.D_H/4;


        //x = getXforCenteredText(text);
        x = 290;
        y += gp.D_W/8;


        g2.drawImage(super.background, 0, 0, gp.D_W, gp.D_H, null);
        if(getOption() == 0){

            g2.drawImage(this.seta, x , y+40, 100, 100, null);
        }



        //x = getXforCenteredText(text);
        y += gp.D_W/10;

        if(getOption() == 1){
            g2.drawImage(this.seta, x + 90, y+30, 100, 100, null);

        }

    }

    public int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.D_W/2 - length/2;
        return x;
    }

    public int getYforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        int y = gp.D_H/2 - length/2;
        return y;
    }
}
