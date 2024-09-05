package entity.enemie;

import entity.Entity;
import entity.player.Player;
import main.GamePanel;
import map.Map1;
import map.MapDefault;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Enemie extends Entity {
    GamePanel gp;
    Player player;
    MapDefault map;
    int xInMap;
    int heigh;
    int width;
    double enemiePosition = 0;
    int xOriginal;
    int yOriginal;
    boolean direita = true;
    public Enemie(GamePanel gp, int x, int y, double aceleracao, Player player, MapDefault map, int lim, double enemiePosition){
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.aceleracao = aceleracao;
        this.player = player;
        this.map = map;
        this.limVelocidade = lim;
        getCarImage();
        xOriginal = this.x;
        yOriginal = this.y;
        this.enemiePosition = enemiePosition;
        if (x<640)
            direita = false;


    }
    public void getCarImage(){
        try {
            carro = ImageIO.read(getClass().getResourceAsStream("/enemie/car1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(){
        acelerar();
        //colisao();
    }

    public void colisao() {
        System.out.println(player.velocidade);
    }

    public void draw(Graphics2D g2)  {
        g2.drawImage(carro, x, y, width, heigh, null);
    }
    public void acelerar(){
        velocidade += aceleracao;
        enemiePosition += velocidade;

        if (velocidade > limVelocidade)
            velocidade = limVelocidade;
    }
    }
