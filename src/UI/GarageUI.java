package UI;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GarageUI extends UI {
    BufferedImage seta,seta_esq, car1,car2;
    Graphics2D g2;
    int carXOffSet = 200;
    int carYOffSet = 370;
    int carImageWidth = 400;
    int carImageHeight = 300;
    public GarageUI(GamePanel gp) {
        super(gp, "/ui/garage.png");
        try {
            seta = ImageIO.read(getClass().getResourceAsStream("/ui/seta.png"));
            seta_esq = ImageIO.read(getClass().getResourceAsStream("/ui/seta_esq.png"));
            // Carregar todas as imagens
            car1 = ImageIO.read(getClass().getResourceAsStream("/ui/car1.png"));
            car2 = ImageIO.read(getClass().getResourceAsStream("/ui/car2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw( Graphics2D g2){
        this.g2 = g2;
        String text = "Garagem";
        int x = 60;
        int y = gp.D_H/4;

        y += gp.D_W/8;


        g2.drawImage(background, 0, 0, gp.D_W, gp.D_H, null);

        int carX = getXforCenteredText(text);
        int carY = getXforCenteredText(text);

        if(getCarIndex() == 0){
            g2.drawImage(car1, carX - carXOffSet, carY - carYOffSet, carImageWidth, carImageHeight, null);
        }
        if(getCarIndex() == 1){
            g2.drawImage(car2, carX - carXOffSet, carY - carYOffSet, carImageWidth, carImageHeight, null);
        }


        if(getCarIndex() != 0){
            g2.drawImage(seta_esq, x - 30 , y, 200, 200, null);
        }

        if(getCarIndex() != getNumberOfCars() -1){
            g2.drawImage(seta, x +1010, y, 200, 200, null);
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
