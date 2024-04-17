package main;
import entity.Player;
import map.Map1;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class GamePanel extends JPanel implements Runnable {

    // CONFIGURAÇÕES DE TELA
    public final int D_W = 1280;
    public final int D_H = 720;
    // TECLADO
    KeyHandler keyH = new KeyHandler();
    //  FPS
    int FPS = 60;

    Thread gameThread;
    // Player
    Player player = new Player(this,keyH);
    Map1 map1 = new Map1(this, keyH, player);

    public GamePanel(){

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
        player.update();
        map1.update();

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //desenharObjetos(g2);

        map1.draw(g2);

        player.draw(g2);

        g2.dispose();

    }

}
