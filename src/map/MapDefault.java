package map;

import entity.enemie.Enemie;
import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class MapDefault {
    GamePanel gp;
    KeyHandler keyH;
    int tamanhoDaPista;
    int roadW = 4000;
    int segL = 600; // segmen lenght
    double camD = 0.6; // camera deph
    public int playerPosition = 0;
    public int playerX = 0;
    public BufferedImage miniMap, point, backgroundUsado;
    public BufferedImage[] background;

    public void desenharQuadrado(Graphics g, Color c, int x1, int y1, int w1, int x2, int y2, int w2) {
        int[] xPoints = {x1 - w1, x2 - w2, x2 + w2, x1 + w1};
        int[] yPoints = {y1, y2, y2, y1};
        int nPoints = 4;
        g.setColor(c);
        g.fillPolygon(xPoints, yPoints, nPoints);
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
}

