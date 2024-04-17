package entity;

import main.GamePanel;
import main.KeyHandler;
import java.awt.*;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public  Player(GamePanel gp, KeyHandler keyH){
        this.gp=gp;
        this.keyH=keyH;

        setDefaultValues();
    }

    public void setDefaultValues(){
        x = 499;
        y = 554;
        aderencia = 7;
        aceleracao = 1.5;
        limVelocidade = 300;
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
        } if (keyH.leftPressed) {
            x -= aderencia;
        } if (keyH.rightPressed) {
            x += aderencia;
        }

        if (!keyH.upPressed){
            if (velocidade>0)
                velocidade -= 2;
            if (velocidade < 0)
                velocidade=0;
        }

        x = Math.max(400, Math.min(1208 - 330, x)); // Limite da tela pro carro não sair
    }
    public void draw(Graphics2D g2){
        g2.setColor(Color.RED);
        g2.fillRect(x,y, 290, 150);
    }
}
