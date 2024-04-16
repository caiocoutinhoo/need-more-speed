package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class GamePanel extends JPanel implements Runnable {

    // CONFIGURAÇÕES DE TELA
    final int D_W = 1280;
    final int D_H = 720;

    // TECLADO

    KeyHandler keyH = new KeyHandler();


    //  FPS
    int FPS = 60;

    // TESTE DE PISTA

    int roadW=2000;
    int segL=200; // segmen lenght
    double camD= 0.7; // camera deph
    List<Line> lines = new ArrayList<GamePanel.Line>();
    int N;

    // Posição do carro
    int playerX = 499;
    int playerY = 554;
    int playerSpeed = 4;


    Thread gameThread;

    public GamePanel(){
        for (int i = 0; i < 1600; i++) {
            Line line = new Line();
            line.z = i*segL;
            lines.add(line);
        }

        N = lines.size();


        this.setPreferredSize( new Dimension (D_W, D_H));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    public void starGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawnInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null){
            //FPS
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawnInterval;
            lastTime = currentTime;

            if (delta >= 1){
                // 1 UPDATE: Atualizar a posição do player
                update();
                // 2 DRAW: Desenha na tela e atualiza o desenho
                repaint();
                delta--;
            }

        }
    }

    public void update(){
        if (keyH.upPressed){
            playerY -= playerSpeed;
        } if (keyH.downPressed) {
            playerY += playerSpeed;
        } if (keyH.leftPressed) {
            playerX -= playerSpeed;
        } if (keyH.rightPressed) {
            playerX += playerSpeed;
        }


        playerX = Math.max(40, Math.min(D_W - 330, playerX));
        playerY = Math.max(0, Math.min(D_H - 150, playerY));
        System.out.println(playerX);
        System.out.println(playerY);

    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        desenharObjetos(g2);

        g2.setColor(Color.RED);
        g2.fillRect(playerX,playerY, 290, 150);
        g2.dispose();

    }
    void desenharObjetos(Graphics2D g){
        // Desenhar ruas

        for (int n = 0; n < 300; n++) {
            Line l= lines.get(n%N);
            l.project(0, 1500, 0);
            Color grass= ((n/2)%2)==0? new Color(16,200,16):new Color(0,154,0);
            Color rumble= ((n/2)%2)==0? new Color(255,255,255):new Color(255,0,0);
            //Color road= ((n/2)%2)==0? new Color(0, 0, 0):new Color(31, 31, 31);
            Color road= Color.black;

            Line p = null;

            if (n == 0) {
                p = l;
            }else {
                p = lines.get((n - 1) % N);
            }

            desenharQuadrado(g, grass, 0, (int) p.Y, D_W, 0, (int) l.Y, D_W);
            desenharQuadrado(g, rumble, (int) p.X, (int) p.Y, (int) (p.W * 1.4), (int) l.X, (int) l.Y,
                    (int) (l.W * 1.4));
            desenharQuadrado(g, road, (int) p.X, (int) p.Y, (int) (p.W * 1.2), (int) l.X, (int) l.Y, (int) (l.W* 1.2));

        }


        // Desenhar céu
        g.setColor(new Color(0x4141EE));
        g.fillRect(0,0, 1280, 456);

    }
    void desenharQuadrado(Graphics g, Color c, int x1, int y1, int w1, int x2, int y2, int w2){
        int[] xPoints={x1-w1,x2-w2,x2+w2,x1+w1};
        int[] yPoints={y1,y2,y2,y1};
        int nPoints=4;
        g.setColor(c);
        g.fillPolygon(xPoints,yPoints,nPoints);
    }

    public class Line{
        double x,y,z; // 3D center of line
        double X,Y,W; // screen coord
        double scale;

        public Line(){
            x=z=y=0;
        }
        void project(int camX, int camY, int camZ){
            scale = camD/(z-camZ);
            X= (1+scale*(x-camX)) * D_W/2;
            Y= (1-scale*(y-camY)) * 900/2;


            W= scale * roadW * D_W/2;

        }


    }

}
