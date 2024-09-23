package map;

import entity.enemie.EnemiesService;
import entity.player.Player;
import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Map3 extends MapDefault{
    public static final int pointsToWin = 40;
    Player player;
    int contadorFrameBack = 0;
    List<Line> lines = new ArrayList<Line>();
    int N;
    int pointX, pointY, backX = -60, backY = -46;
    EnemiesService enemiesService;
    int voltas = 2;

    public Map3(GamePanel gp, KeyHandler keyH, Player player) {
        this.gp = gp;
        this.keyH = keyH;
        this.player = player;
        enemiesService = new EnemiesService(gp, player, this);
        getMap3Image();

        player.velocidade = 0;

        tamanhoDaPista = 3650;
        for (int i = 0; i < ((tamanhoDaPista * voltas) + 500); i++) {
            Line line = new Line();
            line.z = i * segL;
            for (int j = 0; j < voltas; j++) {
                percurssoDoMapa(i, tamanhoDaPista, line, j);
            }
            lines.add(line);
        }
        N = lines.size();
    }

    void drawStreet(Graphics2D g) {
        // Desenhar ruas

        int starPosition = playerPosition / segL;
        double x = 0, dx = 0;
        double maxY = 900;
        int camH = 1500 + (int) lines.get(starPosition).y;

        for (int n = starPosition; n < starPosition + 300; n++) {

            Line l = lines.get(n % N);
            l.project(playerX - (int) x, camH, playerPosition);
            x += dx;
            dx += l.curve;

            if (l.Y > 0 && l.Y < maxY) {
                maxY = l.Y;

                Color grass = ((n / 2) % 2) == 0 ? new Color(31, 54, 63) : new Color(39, 61, 70);
                Color rumble = ((n / 2) % 2) == 0 ? new Color(255, 255, 255) : new Color(255, 117, 0);
                Color road = ((n / 2) % 2) == 0 ? new Color(21, 21, 21) : new Color(0, 0, 0);
                Color midel = ((n / 2) % 2) == 0 ? new Color(255, 255, 255) : new Color(0x000000);

                Line p = null;

                if (n == 0) {
                    p = l;
                } else {
                    p = lines.get((n - 1) % N);
                }

                desenharQuadrado(g, grass, 0, (int) p.Y, gp.D_W, 0, (int) l.Y, gp.D_W);
                desenharQuadrado(g, rumble, (int) p.X, (int) p.Y, (int) (p.W * 1.4), (int) l.X, (int) l.Y, (int) (l.W * 1.4));
                desenharQuadrado(g, road, (int) p.X, (int) p.Y, (int) (p.W * 1.2), (int) l.X, (int) l.Y, (int) (l.W * 1.2));
                desenharQuadrado(g, midel, (int) p.X, (int) p.Y, (int) (p.W * 0.03), (int) l.X, (int) l.Y, (int) (l.W * 0.03));

                enemiesService.createEnemies(playerPosition, segL, lines, g);
            }
        }
    }

    public void getMap3Image() {
        background = new BufferedImage[2];
        try {
            miniMap = ImageIO.read(getClass().getResourceAsStream("/map3/map3.png"));
            point = ImageIO.read(getClass().getResourceAsStream("/map1/point.png"));
            cutsceneImage = ImageIO.read(getClass().getResourceAsStream("/map3/back1.png"));
            backgroundUsado = ImageIO.read(getClass().getResourceAsStream("/map3/back1.png"));
            background[0] = ImageIO.read(getClass().getResourceAsStream("/map3/back1.png"));
            background[1] = ImageIO.read(getClass().getResourceAsStream("/map3/back2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        player.update();
        playerKey();
        curvasPlayer();
        barreirada();
        backgroundUpdate();
        finishRacing();

    }
    private void playerKey() {
        if (keyH.upPressed) {
            playerPosition += (int) player.velocidade;
        }
        if (!keyH.upPressed) {
            playerPosition += (int) player.velocidade;
        }
        if (keyH.leftPressed) {
            playerX -= (int) (player.aderencia * player.velocidade);
            if ((player.aderencia * player.velocidade) >= 5) {
                player.imageX1 = 177;
                player.imageX2 = 233;
            }
        }
        if (keyH.rightPressed) {
            playerX += (int) (player.aderencia * player.velocidade);
            if ((player.aderencia * player.velocidade) >= 5) {
                player.imageX1 = 66;
                player.imageX2 = 123;
            }
        }
        if (!keyH.leftPressed && !keyH.rightPressed) {
            player.imageX1 = 124;
            player.imageX2 = 176;


        }
    }

    public void draw(Graphics2D g2) {
        if (cutscene(g2))
            mapRunning(g2);
    }

    private void mapRunning(Graphics2D g2) {
        background(g2);
        drawStreet(g2);
        g2.drawImage(miniMap, 1050, 50, 130, 130, null);
        player.draw(g2);
        pointInMap(g2);
        enemiesService.updateEnemies(playerPosition, g2);
    }

    public void percurssoDoMapa(int i, int tamanho, Line line, int v) {
        v = v * tamanho;
        if (i > 500 + v && i < 550 + v) {
            line.curve = 10;
        }
        if (i > 600 + v && i < 650 + v) {
            line.curve = -10;
        }
        if (i > 950 + v && i < 1050 + v) {
            line.curve = 15;
        }
        if (i > 1450 + v && i < 1500 + v) {
            line.curve = 5;
        }
        if (i > 1550 + v && i < 1600 + v) {
            line.curve = -5;
        }
        if (i > 1900 + v && i < 1950 + v) {
            line.curve = -15;
        }
        if (i > 1950 + v && i < 2200 + v) {
            line.curve = 8;
        }
        if (i > 2200 + v && i < 2500 + v) {
            line.curve = 2;
        }
        if (i > 2500 + v && i < 2600 + v) {
            line.curve = -7;
        }
        if (i > 2600 + v && i < 2700 + v) {
            line.curve = 7;
        }
        if (i > 3100 + v && i < 3150 + v) {
            line.curve = 12;
        }
        if (i > 3500 + v && i < 3650 + v) {
            line.curve = 13;
        }

    }
    public void curvasPlayer() {
        int tes = this.voltaPercorrida * tamanhoDaPista;

        if (playerPosition / 600 > tes + (500) && playerPosition / 600 < tes + (550)) {
            playerX -= (int) curvaInercia(player.velocidade, 10);
        }
        if (playerPosition / 600 > tes + (600) && playerPosition / 600 < tes + (650)) {
            playerX += (int) curvaInercia(player.velocidade, 10);
        }
        if (playerPosition / 600 > tes + (950) && playerPosition / 600 < tes + (1050)) {
            playerX -= (int) curvaInercia(player.velocidade, 15);
        }
        if (playerPosition / 600 > tes + (1450) && playerPosition / 600 < tes + (1500)) {
            playerX -= (int) curvaInercia(player.velocidade, 5);
        }
        if (playerPosition / 600 > tes + (1550) && playerPosition / 600 < tes + (1600)) {
            playerX -= (int) curvaInercia(player.velocidade, 5);
        }
        if (playerPosition / 600 > tes + (1900) && playerPosition / 600 < tes + (1950)) {
            playerX += (int) curvaInercia(player.velocidade, 15);
        }
        if (playerPosition / 600 > tes + (1950) && playerPosition / 600 < tes + (2200)) {
            playerX -= (int) curvaInercia(player.velocidade, 8);
        }
        if (playerPosition / 600 > tes + (2200) && playerPosition / 600 < tes + (2500)) {
            playerX -= (int) curvaInercia(player.velocidade, 2);
        }
        if (playerPosition / 600 > tes + (2500) && playerPosition / 600 < tes + (2600)) {
            playerX += (int) curvaInercia(player.velocidade, 7);
        }
        if (playerPosition / 600 > tes + (2600) && playerPosition / 600 < tes + (2700)) {
            playerX -= (int) curvaInercia(player.velocidade, 7);
        }
        if (playerPosition / 600 > tes + (3100) && playerPosition / 600 < tes + (3150)) {
            playerX -= (int) curvaInercia(player.velocidade, 12);
        }
        if (playerPosition / 600 > tes + (3500) && playerPosition / 600 < tes + (3650)) {
            playerX -= (int) curvaInercia(player.velocidade, 13);
        }

        if (playerPosition / 600 >= tes + (3650) && playerPosition / 600 <= tes + (3660)) {
            this.voltaPercorrida++;

            System.out.println("--------------------------------------------------------");
        }

    }

    public void barreirada() {
        if ((playerX < -4090 || playerX > 4090) && player.velocidade > 200) {
            player.velocidade -= 30;
        }
    }


    public double curvaInercia(double velocidade, int curva) {
        return ((curva * 6) * velocidade) / 350;
    }

    public void pointInMap(Graphics2D g2) {
        int totalPercorrido = this.voltaPercorrida * tamanhoDaPista;

        g2.drawImage(point, pointX, pointY, 12, 12, null);


        switch ((playerPosition / 600) - totalPercorrido) {
            case 0:
                pointX = 1069;
                pointY = 140;
                break;
            case 30:
                pointY = 138;
                break;
            case 75:
                pointY = 135;
                break;
            case 150:
                pointY = 130;
                break;
            case 225:
                pointY = 125;
                break;
            case 300:
                pointY = 120;

                break;
            case 350:
                pointY = 115;

                break;
            case 400:
                pointY = 110;

                break;
            case 450:
                pointY = 105;

                break;
            case 500:
                pointY = 100;

                break;
            case 550:
                pointY = 95;
                pointX = 1072;
                break;
            case 600:
                pointY = 87;
                pointX = 1075;
                break;
            case 650:
                //nova reta
                pointY = 81;
                pointX = 1068;
                break;
            case 700:
                pointY = 77;
                pointX = 1066;
                break;
            case 750:
                pointY = 73;
                break;
            case 800:
                pointY = 69;
                break;
            case 850:
                pointY = 66;
                break;
            case 900:
                pointY = 63;
                break;
            case 950:
                //final da reta
                pointY = 59;
                break;
            case 1000:
                pointY = 53;
                pointX = 1075;
                break;
            case 1050:
                pointY = 52;
                pointX = 1080;
                break;
            case 1100:
                pointX = 1085;
                break;
            case 1150:

                pointX = 1090;
                break;
            case 1200:

                pointX = 1095;
                break;
            case 1300:

                pointX = 1098;
                break;
            case 1350:

                pointX = 1101;
                break;
            case 1400:

                pointX = 1104;
                break;
            case 1450:

                pointX = 1107;
                break;
            case 1500:
                //final da reta
                pointY = 52;
                pointX = 1110;
                break;

            case 1900:
                pointY = 78;
                pointX = 1149;

                break;

            case 2300:
                pointY = 106;
                pointX = 1122;
                break;

            case 2700:
                pointY = 125;
                pointX = 1118;
                break;

            case 2900:
                pointY = 150;
                pointX = 1116;
                break;
            case 3000:
                pointY = 161;
                pointX = 1075;
                break;

        }

    }

    public void background(Graphics2D g2) {
        backX = -60 + playerX / 200;
        g2.drawImage(backgroundUsado, backX, backY, 794 * 7 / 4, 285 * 7 / 4, null);
    }


    public boolean cutscene(Graphics2D g2) {
        if (cutsceneCounter < 50) {
            player.velocidade = 0;
            cutsceneCounter++;
            g2.drawImage(backgroundUsado, 0, 0, 1280, 720, 0, 0, 507, 285, null);
            return false; // Retorna true após 50 execuções
        }
        if (cutsceneCounter > 49 && cutsceneCounter < 200) {
            player.velocidade = 0;
            cutsceneCounter++;
            g2.drawImage(backgroundUsado, 0, 0, 1280, 720, 0, 0, 507, 285, null);
            fadeToBlack(g2);
            return false;
        }
        return true;
    }

    public void backgroundUpdate() {
        contadorFrameBack++;
        if (contadorFrameBack  > 120) {
            backgroundUsado = background[1];
        }
        if (contadorFrameBack > 130){
            contadorFrameBack = 0;
            backgroundUsado = background[0];
        }
    }
    @Override
    public int finishRacing() {
        // 0 = corrida iniciada, porém não terminou
        // 1 = corrida terminou, jogador ganhou
        // 2 = corrida terminou, jogador perdeu
        // 3 = fim de jogo
        if (((tamanhoDaPista * voltas) <= (playerPosition/600)) && (playerPoints >= pointsToWin)){
            System.out.println("GANHOU");



            return 3;
        }
        if (tamanhoDaPista * voltas <= playerPosition/600 && playerPoints < pointsToWin ){
            System.out.println("PERDEU");

            return 2;
        }
        return 0;
    }
}
