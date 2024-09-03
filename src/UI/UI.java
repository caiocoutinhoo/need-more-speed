package UI;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class UI {
    GamePanel gp;
    Graphics2D g2;
    BufferedImage  background;
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
}
