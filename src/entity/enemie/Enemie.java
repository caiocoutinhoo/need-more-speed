package entity.enemie;

import entity.Entity;
import entity.player.Player;
import main.GamePanel;
import map.MapDefault;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Enemie extends Entity {
    GamePanel gp;
    Player player;
    MapDefault map;
    double xInMap;
    double enemiePosition = 0;
    int xOriginal;
    int yOriginal;
    boolean overtaken = false;
    public static int carImageTexture = 0;
    String urlImage;

    public Enemie(GamePanel gp, int x, int y, double aceleracao, Player player, MapDefault map, int lim, double enemiePosition, double xInMap) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.aceleracao = aceleracao;
        this.player = player;
        this.map = map;
        this.limVelocidade = lim;
        setCarImage();
        xOriginal = this.x;
        yOriginal = this.y;
        this.enemiePosition = enemiePosition;
        this.xInMap = xInMap;

    }

    public double getEnemiePosition() {
        return enemiePosition;
    }

    public void setCarImage() {
        switch (carImageTexture) {
            case 0:
                urlImage = "/enemie/uno.png";
                carImageTexture++;
                break;
            case 1:
                urlImage = "/enemie/hiluxsw4.png";
                carImageTexture++;
                break;
            case 2:
                urlImage = "/enemie/kwid.png";
                carImageTexture = 0;
                break;
        }
        try {
            carro = ImageIO.read(getClass().getResourceAsStream(urlImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        acelerar();
        //colisao();
    }

    public void checkOvertaken(MapDefault map) {
        if (map.playerPosition > enemiePosition && !overtaken) {
            map.playerPoints++;
            System.out.println("Player ganhou 1 ponto! Total de pontos: " + map.playerPoints);
            this.overtaken = true;
        }
    }

    public void acelerar() {
        velocidade = limVelocidade;
        enemiePosition += velocidade;
    }
}
