package UI;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GarageUI extends UI {
    BufferedImage seta,seta_esq, car1,car2,car3;
    Graphics2D g2;
    int carXOffSet = 200;
    int carYOffSet = 370;
    int carImageWidth = 150;
    int carImageHeight = 200;
    int forward = -240;
    public GarageUI(GamePanel gp) {
        super(gp, "/ui/garage.png");
        try {
            seta = ImageIO.read(getClass().getResourceAsStream("/ui/seta.png"));
            seta_esq = ImageIO.read(getClass().getResourceAsStream("/ui/seta_esq.png"));
            // Carregar todas as imagens
            car1 = ImageIO.read(getClass().getResourceAsStream("/ui/car1.png"));
            car2 = ImageIO.read(getClass().getResourceAsStream("/ui/car2.png"));
            car3 = ImageIO.read(getClass().getResourceAsStream("/ui/car3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw( Graphics2D g2){
        this.g2 = g2;

        int car1xPos = gp.D_W - 1250;;
        int car1yPos = gp.D_H - 200;

        int car2xPos = gp.D_W - 1025;;
        int car2yPos = gp.D_H - 200;

        int car3xPos = gp.D_W - 800;;
        int car3yPos = gp.D_H - 200;


        g2.drawImage(background, 0, 0, gp.D_W, gp.D_H, null);

        if(getCarIndex() == 0) {
            car1yPos += forward;
            car2yPos = gp.D_H - 200;
        }
        if(getCarIndex() == 1) {
            car2yPos += forward;
            car1yPos = gp.D_H - 200;
        }
        if(getCarIndex() == 2) {
            car3yPos += forward;
            car2yPos = gp.D_H - 200;
        }
        g2.drawImage(car1, car1xPos , car1yPos , carImageWidth, carImageHeight, null);
        g2.drawImage(car2, car2xPos, car2yPos, carImageWidth, carImageHeight, null);
        g2.drawImage(car3, car3xPos, car3yPos, carImageWidth, carImageHeight, null);


    }
}
