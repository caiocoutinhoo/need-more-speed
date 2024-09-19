package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Entity {
    public int x, y;
    public double aderencia;
    public double aceleracao;
    public double velocidade=0;
    public int limVelocidade;
    public int freio;
    public BufferedImage carro;
    public int imageX1,imageY1, imageX2,imageY2;
    public int width,height;

    public void setCarImage(String index){
        try {
            //int index = getcarID();
            carro = ImageIO.read(getClass().getResourceAsStream(index));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
