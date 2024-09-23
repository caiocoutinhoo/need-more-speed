package entity.player;

import entity.Entity;
import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

import static entity.player.PlayerConfig.*;
import static entity.player.PlayerConfig.pheight;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    int temp=0;
    double countVelocity = 0;

    public Player(GamePanel gp, KeyHandler keyH){
        this.x = DX1;
        this.y = DY1;
        this.gp = gp;
        this.keyH = keyH;
        this.width = pwidth;
        this.height = pheight;
        getCarImage();
        setDefaultValues();

    }
    public void getCarImage(){
        try {
            carro = ImageIO.read(getClass().getResourceAsStream("/car1/car1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setDefaultValues(){
        aderencia = 0.2;
        aceleracao = 3;
        limVelocidade = 700;
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
        keywordPlayerControl();
        spriteCarVelocity();
    }

    private void keywordPlayerControl() {
        if (keyH.upPressed){
            velocidade +=  aceleracao;
            if (velocidade > limVelocidade)
                velocidade = limVelocidade;
        }
        if (keyH.downPressed) {
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
    }

    public void draw(Graphics2D g2)  {
        g2.drawImage(carro,512,550,767,700,imageX1, imageY1, imageX2,imageY2,null);
    }

    public void spriteCarVelocity(){
        countVelocity += velocidade;

        if (countVelocity >= 800 && imageY1 >60){
            imageY1 -= 62;
            imageY2 -= 62;
            countVelocity = 0;
        }
        if (countVelocity >= 800){
            imageY1 += 31;
            imageY2 += 31;
            countVelocity = 0;
        }
    }

    public void onCollision() {
        this.velocidade = this.velocidade - 50;
    }
}
