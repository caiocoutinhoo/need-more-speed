package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    int temp=0;
    double contador1 = 0;
    private int carID;


    public Player(GamePanel gp, KeyHandler keyH){
        this.gp=gp;
        this.keyH=keyH;
        getCarImage();
        setDefaultValues();

    }
    public void getCarImage(){
        try {
            //int index = getcarID();
            carro = ImageIO.read(getClass().getResourceAsStream("/car1/car1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setDefaultValues(){
        aderencia = 0.15;
        aceleracao = 2;
        limVelocidade = 600;
        freio = 10;
        defaultImage();

    }
    public void defaultImage(){
        imageX1 = 124;
        imageY1 = 0;
        imageX2 = 176;
        imageY2 = 31;
    }
    public void update(){
        if (keyH.upPressed){
            velocidade +=  aceleracao;
            if (velocidade > limVelocidade)
                velocidade = limVelocidade;
        } if (keyH.downPressed) {
            velocidade -= freio;
            if (velocidade <= 0)
                velocidade =0;
        }
        if (!keyH.upPressed){
            if (velocidade>0)
                velocidade -= 0.5;
            if (velocidade < 0)
                velocidade=0;
        }
        carroPneuVelocidade();

        //verificarVelocidade();
    }
    public void draw(Graphics2D g2)  {
        g2.drawImage(carro,512,550,767,700,imageX1, imageY1, imageX2,imageY2,null);
    }
    public void carroPneuVelocidade(){
        contador1 += velocidade;

        if (contador1 >= 800 && imageY1 >60){
            imageY1 -= 62;
            imageY2 -= 62;
            contador1 = 0;
        }
        if (contador1 >= 800){
            imageY1 += 31;
            imageY2 += 31;
            contador1 = 0;
        }
    }
    void verificarVelocidade(){
        temp +=  60;
        if (temp==600){
            int veloc = (int) (velocidade/2);
            System.out.println("Velocidade:" + veloc);
            temp = 0;
        }

    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }
}
