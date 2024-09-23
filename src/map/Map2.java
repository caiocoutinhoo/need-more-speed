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

public class Map2 extends MapDefault {
    public static final int pointsToWin = 10;
    Player player;
    int contadorFrameBack = 0;
    List<Line> lines = new ArrayList<Line>();
    int N;
    int pointX, pointY, backX = -60, backY = -46;
    EnemiesService enemiesService;
    int voltas = 1;

    public Map2(GamePanel gp, KeyHandler keyH, Player player) {
        this.gp = gp;
        this.keyH = keyH;
        this.player = player;
        enemiesService = new EnemiesService(gp, player, this);
        getMap2Image();

        player.velocidade = 0;


        tamanhoDaPista = 2600;
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

                Color grass = ((n / 2) % 2) == 0 ? new Color(237, 230, 156) : new Color(217, 183, 117);
                Color rumble = ((n / 2) % 2) == 0 ? new Color(255, 255, 255) : new Color(0, 111, 255);
                Color road = ((n / 2) % 2) == 0 ? new Color(100, 84, 64) : new Color(133, 119, 102);
                Color midel = ((n / 2) % 2) == 0 ? new Color(255, 255, 255) : new Color(0x857766);

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

    public void getMap2Image() {
        try {
            miniMap = ImageIO.read(getClass().getResourceAsStream("/map2/map2.png"));
            point = ImageIO.read(getClass().getResourceAsStream("/map1/point.png"));
            cutsceneImage = ImageIO.read(getClass().getResourceAsStream("/map2/back.png"));
            backgroundUsado = ImageIO.read(getClass().getResourceAsStream("/map2/back.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        player.update();
        playerKey();
        curvasPlayer();
        barreirada();

        contadorFrameBack += 1;
        if (contadorFrameBack == 12 * 8)
            contadorFrameBack = 0;

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
        if (i > 150 + v && i < 350 + v) {
            line.curve = 4;
        }
        if (i > 350 + v && i < 400 + v) {
            line.curve = -15;
        }
        if (i > 400 + v && i < 450 + v) {
            line.curve = 15;
        }
        if (i > 650 + v && i < 750 + v) {
            line.curve = 15;
        }
        if (i > 1600 + v && i < 1700 + v) {
            line.curve = 16;
        }
        if (i > 2000 + v && i < 2150 + v) {
            line.curve = 10;
        }
        if (i > 2250 + v && i < 2500 + v) {
            line.curve = 4;
        }

    }

    public void barreirada() {
        if ((playerX < -4090 || playerX > 4090) && player.velocidade > 200) {
            player.velocidade -= 30;
        }
    }

    public void curvasPlayer() {
        int tes = this.voltaPercorrida * tamanhoDaPista;

        if (playerPosition / 600 > tes + (150) && playerPosition / 600 < tes + (350)) {
            playerX -= (int) curvaInercia(player.velocidade, 4);
        }
        if (playerPosition / 600 > tes + (350) && playerPosition / 600 < tes + (400)) {
            playerX += (int) curvaInercia(player.velocidade, 15);
        }
        if (playerPosition / 600 > tes + (400) && playerPosition / 600 < tes + (450)) {
            playerX -= (int) curvaInercia(player.velocidade, 15);
        }
        if (playerPosition / 600 > tes + (650) && playerPosition / 600 < tes + (750)) {
            playerX -= (int) curvaInercia(player.velocidade, 15);
        }
        if (playerPosition / 600 > tes + (1600) && playerPosition / 600 < tes + (1700)) {
            playerX -= (int) curvaInercia(player.velocidade, 16);
        }
        if (playerPosition / 600 > tes + (2000) && playerPosition / 600 < tes + (2150)) {
            playerX -= (int) curvaInercia(player.velocidade, 10);
        }
        if (playerPosition / 600 > tes + (2250) && playerPosition / 600 < tes + (2500)) {
            // line.curve = 12;
            playerX -= (int) curvaInercia(player.velocidade, 4);

        }
        if (playerPosition / 600 >= tes + (2500) && playerPosition / 600 <= tes + (2510)) {
            this.voltaPercorrida++;

            System.out.println("--------------------------------------------------------");
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
                pointX = 1083;
                pointY = 120;
                break;

            case 400:
                pointX = 1100;
                pointY = 75;
                break;

            case 800:
                pointX = 1128;
                pointY = 75;
                break;

            case 1200:

                pointY = 115;
                break;

            case 1600:
                pointX = 1124;
                pointY = 149;
                break;

            case 2100:
                pointX = 1079;
                pointY = 159;
                break;

            case 2700:
                pointX = 1063;
                pointY = 200;
                break;

            case 3000:
                pointX = 1063;
                pointY = 200;
                break;

        }

    }

    public void background(Graphics2D g2) {
        backX = -60 + playerX / 200;
        g2.drawImage(backgroundUsado, backX, backY, 794 * 7 / 4, 285 * 7 / 4, null);
    }

    public boolean cutscene(Graphics2D g2) {
        if (cutsceneCounter < 50) {
            cutsceneCounter++;
            g2.drawImage(backgroundUsado, 0, 0, 1280, 720, 0, 0, 507, 285, null);
            return false; // Retorna true após 50 execuções
        }
        if (cutsceneCounter > 49 && cutsceneCounter < 200) {
            cutsceneCounter++;
            g2.drawImage(backgroundUsado, 0, 0, 1280, 720, 0, 0, 507, 285, null);
            fadeToBlack(g2);
            return false;
        }
        return true;
    }


    @Override
    public int finishRacing() {
        // 0 = corrida iniciada, porém não terminou
        // 1 = corrida terminou, jogador ganhou
        // 2 = corrida terminou, jogador terminou
        if (((tamanhoDaPista * voltas) <= (playerPosition/600)) && (playerPoints >= pointsToWin)){
            System.out.println("GANHOU");

            return 1;
        }
        if (tamanhoDaPista * voltas <= playerPosition/600 && playerPoints < pointsToWin ){
            System.out.println("PERDEU");

            return 2;
        }
        return 0;
    }
}
