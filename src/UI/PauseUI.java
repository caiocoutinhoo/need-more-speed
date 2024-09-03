package UI;

import main.GamePanel;

import java.awt.*;

public class PauseUI extends UI{
    public PauseUI(GamePanel gp) {
        super(gp, "/ui/pauseBackground.png");
    }

    @Override
    public void draw(Graphics2D g2){

        g2.drawImage(background, 0, 0, gp.D_W, gp.D_H, null);

    }
}
