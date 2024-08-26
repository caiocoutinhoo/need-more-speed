package entity.enemie;

import entity.Entity;
import entity.player.Player;
import main.GamePanel;
import map.Map1;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Enemie extends Entity {
    GamePanel gp;
    Player player;
    Map1 map1;
    int xInMap;
    int heigh;
    int width;
    double enemiePosition = 0;
    int xOriginal;
    int yOriginal;
    boolean direita = true;
    public Enemie(GamePanel gp, int x, int y, double aceleracao, Player player, Map1 map1, int lim, double enemiePosition){
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.aceleracao = aceleracao;
        this.player = player;
        this.map1 = map1;
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

        heigh = (int) ((30*5) + ( map1.playerPosition - enemiePosition)/300);
        if (heigh<0)
            heigh = 0;
        double h = 48* ((double) heigh /30);
        width = (int) (h);

        if (heigh>30*5.5){
            width = (int) (48*5.5);
            heigh= (int) (30*5.5);

        }

        y = (int) ((yOriginal) + ( map1.playerPosition - enemiePosition)/400);
        if (y < 460) //452
            y=460;

        if (!direita){
            x = (int) ((xOriginal) - ( map1.playerPosition - enemiePosition)/100);
            if (x > 638)
                x = 638;
        }
        if (direita){
            x = (int) ((xOriginal) + ( map1.playerPosition - enemiePosition)/100);
            if (x < 642)
                x = 642;
        }

        if (heigh<0)
            heigh = heigh*-1;

        x = x - (map1.playerX)/9;

        colisao();
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
