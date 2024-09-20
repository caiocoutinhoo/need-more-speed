package map;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MapDefault {
    GamePanel gp;
    KeyHandler keyH;
    int tamanhoDaPista;
    int roadW = 4000;
    int segL = 600; // segmen lenght
    double camD = 0.5; // camera deph
    public int playerPosition = 0;
    public int playerX = 0;
    public BufferedImage miniMap, point, backgroundUsado, cutsceneImage;
    public BufferedImage[] background;
    public int playerPoints = 0;
    public int voltaPercorrida=0;
    public int cutsceneCounter = 0;
    public float opacity = 0.0f; // Transparência inicial (0 = totalmente transparente, 1 = totalmente opaco)
    public boolean transitioning = false;


    public void desenharQuadrado(Graphics g, Color c, int x1, int y1, int w1, int x2, int y2, int w2) {
        int[] xPoints = {x1 - w1, x2 - w2, x2 + w2, x1 + w1};
        int[] yPoints = {y1, y2, y2, y1};
        int nPoints = 4;
        g.setColor(c);
        g.fillPolygon(xPoints, yPoints, nPoints);
    }

    public void update() {
    }

    public void draw(Graphics2D g2) {
    }

    public class Line {
        public double x, y, z; // 3D center of line
        public double X, Y, W; // screen coord
        double scale, curve;

        public Line() {
            curve = x = z = y = 0;
        }

        public void project(int camX, int camY, int camZ) {
            scale = camD / (z - camZ);
            X = (1 + scale * (x - camX)) * gp.D_W / 2;
            Y = (1 - scale * (y - camY)) * 900 / 2;

            W = scale * roadW * gp.D_W / 2;

        }
    }

    public void fadeToBlack(Graphics2D g2) {
        // Desenha uma camada preta por cima da tela com opacidade crescente
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, 1280, 720);

        // Incrementa a opacidade a cada frame (controla a velocidade da transição)
        opacity += 0.003f; // Ajuste este valor para acelerar ou desacelerar a transição
        if (opacity > 1.0f) {
            opacity = 1.0f; // Garante que a opacidade não ultrapasse o máximo
        }

        // Restaura a opacidade padrão
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
    }

}

