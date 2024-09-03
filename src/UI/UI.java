package UI;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class UI {
    GamePanel gp;
    Graphics2D g2;
    BufferedImage  background, pauseIcon;
    public boolean selected = false;
    private int carIndex = 0;
    private int numberOfCars = 3;
    private int option = 0;
    public int commandNum = 0;


    public UI(GamePanel gp, String backgroundImageURL){
        this.gp = gp;
        try{
            background = ImageIO.read(getClass().getResourceAsStream(backgroundImageURL));
            //garageBackground = ImageIO.read(getClass().getResourceAsStream("/ui/garage.png"));

            pauseIcon = ImageIO.read(getClass().getResourceAsStream("/ui/pause.png"));


        }catch (IOException e){
            e.printStackTrace();
        }
    }

    Font arial_40 = new Font ("Monospaced", Font.PLAIN, 80);



    public int getCarIndex() {
        return carIndex;
    }

    public void setCarIndex(int carIndex) {
        if(carIndex >= 0 && carIndex <= (numberOfCars - 1)){
            this.carIndex = carIndex;
        }

    }

    public void setOption(int option){
        this.option = option;
    }

    public int getOption(){
        return option;
    }
    public int getNumberOfCars() {
        return numberOfCars;
    }

    public abstract void draw(Graphics2D g2);

    public void drawPause(){
        Color cinza = new Color(49, 49, 49, 128);
        g2.setColor(cinza);
        g2.fillRect(0,0, gp.D_W, gp.D_H);
        g2.setColor(Color.white);
        String text = "PAUSED";
        int x = getXforCenteredText(text) ;
        int y = gp.D_H/2;

        g2.drawString(text, x,y);

    }

    public void setG2(Graphics2D g2){
        this.g2 = g2;
    }

    public int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.D_W/2 - length/2;
        return x;
    }
}
