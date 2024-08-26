package map;

import entity.enemie.Enemie;
import entity.player.Player;
import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Map1 extends MapDefault {
    Player player;
    int contadorFrameBack = 0;
    List<Line> lines = new ArrayList<Line>();
    int N;
    int pointX, pointY, backX = -60, backY = -34;
    int voltaPercorrida = 0;
  //  Enemie[] enemies = new Enemie[5];

    public Map1(GamePanel gp, KeyHandler keyH, Player player) {
        this.gp = gp;
        this.keyH = keyH;
        this.player = player;
        getMap1Image();
        enemieCreate();

        tamanhoDaPista = 3000;
        int voltas = 3;
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

    void desenharObjetos(Graphics2D g) {

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

                Color grass = ((n / 2) % 2) == 0 ? new Color(112, 191, 151) : new Color(102, 189, 120);
                Color rumble = ((n / 2) % 2) == 0 ? new Color(255, 255, 255) : new Color(255, 0, 0);
                Color road = ((n / 2) % 2) == 0 ? new Color(117, 117, 117) : new Color(84, 84, 84);
                Color midel = ((n / 2) % 2) == 0 ? new Color(255, 255, 255) : new Color(0x545454);

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
            }
        }
    }

    public void getMap1Image() {
        background = new BufferedImage[13];
        try {
            miniMap = ImageIO.read(getClass().getResourceAsStream("/map1/miniMap-01.png"));
            point = ImageIO.read(getClass().getResourceAsStream("/map1/point.png"));
            backgroundUsado = ImageIO.read(getClass().getResourceAsStream("/map1/back/pixil-frame-0.png"));
            background[0] = ImageIO.read(getClass().getResourceAsStream("/map1/back/pixil-frame-0.png"));
            background[1] = ImageIO.read(getClass().getResourceAsStream("/map1/back/pixil-frame-1.png"));
            background[2] = ImageIO.read(getClass().getResourceAsStream("/map1/back/pixil-frame-2.png"));
            background[3] = ImageIO.read(getClass().getResourceAsStream("/map1/back/pixil-frame-3.png"));
            background[4] = ImageIO.read(getClass().getResourceAsStream("/map1/back/pixil-frame-4.png"));
            background[5] = ImageIO.read(getClass().getResourceAsStream("/map1/back/pixil-frame-5.png"));
            background[6] = ImageIO.read(getClass().getResourceAsStream("/map1/back/pixil-frame-6.png"));
            background[7] = ImageIO.read(getClass().getResourceAsStream("/map1/back/pixil-frame-7.png"));
            background[8] = ImageIO.read(getClass().getResourceAsStream("/map1/back/pixil-frame-8.png"));
            background[9] = ImageIO.read(getClass().getResourceAsStream("/map1/back/pixil-frame-9.png"));
            background[10] = ImageIO.read(getClass().getResourceAsStream("/map1/back/pixil-frame-10.png"));
            background[11] = ImageIO.read(getClass().getResourceAsStream("/map1/back/pixil-frame-11.png"));
            background[12] = ImageIO.read(getClass().getResourceAsStream("/map1/back/pixil-frame-12.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enemieCreate() {

        Enemie enemieLeft = new Enemie(gp, 200, 550, 1, player, this, 400, 0);
        Enemie enemieRight = new Enemie(gp, 1080, 550, 1, player, this, 500, 0);

    }

    public void update() {

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
        //enemies[0].update();
        //enemies[1].update();

        curvasPlayer();
        barreirada();

        contadorFrameBack += 1;
        if (contadorFrameBack == 12 * 8)
            contadorFrameBack = 0;
    }

    public void draw(Graphics2D g2) {
        background(g2);
        desenharObjetos(g2);

        g2.drawImage(miniMap, 1050, 50, 130, 130, null);
        //enemies[0].draw(g2);
        //enemies[1].draw(g2);

        player.draw(g2);
        pointInMap(g2);
    }

    public void percurssoDoMapa(int i, int tamanho, Line line, int v) {
        v = v * tamanho;
        if (i > 300 + v && i < 350 + v) {
            line.curve = 15;
        }
        if (i > 350 + v && i < 450 + v) {
            line.curve = -7;
        }
        if (i > 520 + v && i < 570 + v) {
            line.curve = 15;
        }
        if (i > 700 + v && i < 750 + v) {
            line.curve = 15;
        }
        if (i > 850 + v && i < 1000 + v) {
            line.curve = 0.7;
        }
        if (i > 1070 + v && i < 1500 + v) {
            line.curve = -1.5;
        }
        if (i > 1550 + v && i < 1700 + v) {
            line.curve = 12;
        }
        if (i > 1900 + v && i < 2000 + v) {
            line.curve = 15;
        }
        if (i > 2150 + v && i < 2170 + v) {
            line.curve = -20;
        }
        if (i > 2170 + v && i < 2190 + v) {
            line.curve = 20;
        }
        if (i > 2195 + v && i < 2215 + v) {
            line.curve = 20;
        }
        if (i > 2215 + v && i < 2235 + v) {
            line.curve = -20;
        }
        if (i > 2450 + v && i < 3000 + v) {
            line.curve = 3;
        }

    }

    public void barreirada() {
        if ((playerX < -4040 || playerX > 4040) && player.velocidade > 120) {
            player.velocidade -= 12;
        }
    }

    public void curvasPlayer() {
        int tes = voltaPercorrida * tamanhoDaPista;

        if (playerPosition / 600 > tes + (300) && playerPosition / 600 < tes + (350)) {
            playerX -= (int) curvaInercia(player.velocidade, 15);
        }
        if (playerPosition / 600 > tes + (350) && playerPosition / 600 < tes + (450)) {
            playerX += (int) curvaInercia(player.velocidade, 7);
        }
        if (playerPosition / 600 > tes + (520) && playerPosition / 600 < tes + (570)) {
            //line.curve = 15;
            playerX -= (int) curvaInercia(player.velocidade, 15);

        }
        if (playerPosition / 600 > tes + (700) && playerPosition / 600 < tes + (750)) {
            //line.curve = 15;
            playerX -= (int) curvaInercia(player.velocidade, 15);

        }
        if (playerPosition / 600 > tes + (850) && playerPosition / 600 < tes + (1000)) {
            //line.curve = 0.7;
            playerX -= (int) curvaInercia(player.velocidade, 1);

        }
        if (playerPosition / 600 > tes + (1070) && playerPosition / 600 < tes + (1500)) {
            // line.curve = -1.5;
            playerX += (int) curvaInercia(player.velocidade, 7);

        }
        if (playerPosition / 600 > tes + (1550) && playerPosition / 600 < tes + (1700)) {
            // line.curve = 12;
            playerX -= (int) curvaInercia(player.velocidade, 12);

        }
        if (playerPosition / 600 > tes + (1900) && playerPosition / 600 < tes + (2000)) {
            // line.curve = 15;
            playerX -= (int) curvaInercia(player.velocidade, 15);

        }
        if (playerPosition / 600 > tes + (2150) && playerPosition / 600 < tes + (2170)) {
            // line.curve = -20;
            playerX += (int) curvaInercia(player.velocidade, 20);

        }
        if (playerPosition / 600 > tes + (2170) && playerPosition / 600 < tes + (2190)) {
            // line.curve = 20;
            playerX -= (int) curvaInercia(player.velocidade, 20);

        }
        if (playerPosition / 600 > tes + (2195) && playerPosition / 600 < tes + (2215)) {
            // line.curve = 20;
            playerX -= (int) curvaInercia(player.velocidade, 20);

        }
        if (playerPosition / 600 > tes + (2215) && playerPosition / 600 < tes + (2235)) {
            // line.curve = -20;
            playerX += (int) curvaInercia(player.velocidade, 20);

        }
        if (playerPosition / 600 > tes + (2450) && playerPosition / 600 < tes + (3000)) {
            // line.curve = 3;
            playerX -= (int) curvaInercia(player.velocidade, 3);

        }

        if (playerPosition / 600 >= tes + (3000) && playerPosition / 600 <= tes + (3010)) {
            voltaPercorrida++;

            System.out.println("--------------------------------------------------------");
        }

    }

    public double curvaInercia(double velocidade, int curva) {
        return ((curva * 6) * velocidade) / 350;
    }

    public void pointInMap(Graphics2D g2) {
        int totalPercorrido = voltaPercorrida * tamanhoDaPista;

        g2.drawImage(point, pointX, pointY, 12, 12, null);


        switch ((playerPosition / 600) - totalPercorrido) {
            case 0:
                pointX = 1069;
                pointY = 130;
                break;
            case 30:
                pointY = 125;
                break;
            case 75:
                pointY = 115;
                break;
            case 150:
                pointY = 105;
                break;
            case 225:
                pointY = 85;
                break;
            case 300:
                pointY = 78;
                pointX = 1072;
                break;
            case 350:
                pointY = 72;
                pointX = 1085;
                break;
            case 400:
                pointX = 1087;
                break;
            case 450:
                pointY = 59;
                pointX = 1091;
                break;
            case 500:
                pointY = 53;
                pointX = 1094;
                break;
            case 550:
                pointX = 1098;
                break;
            case 600:
                pointX = 1102;
                break;
            case 650:
                pointX = 1108;
                break;
            case 700:
                pointY = 58;
                pointX = 1118;
                break;
            case 750:
                pointY = 61;
                break;
            case 800:
                pointY = 62;
                break;
            case 850:
                pointY = 64;
                break;
            case 900:
                pointY = 65;
                break;
            case 950:
                pointY = 66;
                break;
            case 1000:
                pointY = 68;
                break;
            case 1050:
                pointY = 73;
                break;
            case 1100:
                pointY = 74;
                break;
            case 1150:
                pointY = 75;
                pointX = 1116;
                break;
            case 1200:
                pointY = 77;
                pointX = 1114;
                break;
            case 1300:
                pointY = 81;
                pointX = 1111;
                break;
            case 1350:
                pointY = 87;
                pointX = 1103;
                break;
            case 1400:
                pointY = 95;
                pointX = 1100;
                break;
            case 1450:
                pointY = 103;
                pointX = 1101;
                break;
            case 1500:
                pointY = 111;
                pointX = 1106;
                break;
            case 1550:
                pointY = 119;
                pointX = 1111;
                break;
            case 1600:
                pointY = 133;
                pointX = 1120;
                break;
            case 1650:
                pointY = 140;
                pointX = 1133;
                break;
            case 1700:
                pointY = 150;
                pointX = 1135;
                break;
            case 1800:
                pointY = 156;
                break;
            case 1900:
                pointY = 163;
                pointX = 1133;
                break;
            case 2100:
                pointY = 165;
                pointX = 1130;
                break;
            case 2150:
                pointY = 168;
                pointX = 1121;
                break;
            case 2250:
                pointY = 169;
                pointX = 1117;
                break;
            case 2300:
                pointX = 1111;
                break;
            case 2400:
                pointX = 1104;
                break;
            case 2450:
                pointX = 1102;
                break;
            case 2500:
                pointY = 161;
                pointX = 1091;
                break;
            case 2600:
                pointY = 157;
                pointX = 1086;
                break;
            case 2700:
                pointY = 153;
                pointX = 1081;
                break;
            case 2800:
                pointY = 148;
                pointX = 1079;
                break;
            case 2900:
                pointY = 142;
                pointX = 1075;
                break;
            case 3000:
                pointY = 130;
                pointX = 1069;
                break;

        }

    }

    public void background(Graphics2D g2) {
        switch (contadorFrameBack) {
            case 0:
                backgroundUsado = background[0];
                break;
            case 8:
                backgroundUsado = background[1];
                break;
            case 2 * 8:
                backgroundUsado = background[2];
                break;
            case 3 * 8:
                backgroundUsado = background[3];
                break;
            case 4 * 8:
                backgroundUsado = background[4];
                break;
            case 5 * 8:
                backgroundUsado = background[5];
                break;
            case 6 * 8:
                backgroundUsado = background[6];
                break;
            case 7 * 8:
                backgroundUsado = background[7];
                break;
            case 8 * 8:
                backgroundUsado = background[8];
                break;
            case 9 * 8:
                backgroundUsado = background[9];
                break;
            case 10 * 8:
                backgroundUsado = background[10];
                break;
            case 11 * 8:
                backgroundUsado = background[11];
                break;
            case 12 * 8:
                backgroundUsado = background[12];
                break;

        }
        backX = -60 + playerX / 200;
        g2.drawImage(backgroundUsado, backX, backY, 794 * 7 / 4, 285 * 7 / 4, null);
    }
}


