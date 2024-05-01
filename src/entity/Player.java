package entity;

import main.GamePanel;
import main.KeyHandler;
import java.awt.*;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    int temp=0;

    public  Player(GamePanel gp, KeyHandler keyH){
        this.gp=gp;
        this.keyH=keyH;

        setDefaultValues();
    }

    public void setDefaultValues(){
        x = 499;
        y = 554;
        aderencia = 0.2;
        aceleracao = 1;
        limVelocidade = 500;
        freio = 10;
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

        x = Math.max(10, Math.min(1208 - 300, x)); // Limite da tela pro carro não sair
        //verificarVelocidade();
    }
    public void draw(Graphics2D g2)  {
        g2.setColor(Color.RED);
        g2.fillRect(x,y, 290, 150);
    }

    void verificarVelocidade(){
        temp +=  60;
        if (temp==600){
            int veloc = (int) (velocidade/2);
            System.out.println("Velocidade:" + veloc);
            temp = 0;
        }

    }
}
