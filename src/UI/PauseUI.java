package UI;

import main.GamePanel;

import java.awt.*;

public class PauseUI extends UI{
    Graphics2D g2;
    public PauseUI(GamePanel gp) {
        super(gp, "/ui/pauseBackground.png");
    }

    @Override
    public void draw(Graphics2D g2){
        this.g2 = g2;
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
