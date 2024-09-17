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
    double xInMap;
    int heigh;
    int width;
    double enemiePosition = 0;
    int xOriginal;
    int yOriginal;
    boolean overtaken = false;

    public Enemie(GamePanel gp, int x, int y, double aceleracao, Player player, MapDefault map, int lim, double enemiePosition, double xInMap){
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
        this.xInMap = xInMap;

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

    public void checkOvertaken(MapDefault map){
        if (map.playerPosition > enemiePosition && !overtaken){
            map.playerPoints++;
            System.out.println("Player ganhou 1 ponto! Total de pontos: " + map.playerPoints);
            this.overtaken = true;
        }
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
