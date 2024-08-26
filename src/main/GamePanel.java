package main;

import entity.player.Player;
import map.Map1;

import javax.swing.*;
import java.awt.*;


public class GamePanel extends JPanel implements Runnable {

    // CONFIGURAÇÕES DE TELA
    public final int D_W = 1280;
    public final int D_H = 720;
    // TECLADO
    KeyHandler keyH = new KeyHandler(this);
    //  FPS
    int FPS = 60;
    // UI
    public UI ui = new UI(this);
    Thread gameThread;
    // Player
    Player player = new Player(this, keyH);
    Map1 map1 = new Map1(this, keyH, player);
    //GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;

    public GamePanel() {
        this.setPreferredSize(new Dimension(D_W, D_H));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void starGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
        gameState = titleState;
    }

    @Override
    public void run() {
        double drawnInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            //FPS
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawnInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                // 1 UPDATE: Atualizar a posição do player
                update();
                // 2 DRAW: Desenha na tela e atualiza o desenho
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        if (gameState == playState) {

            map1.update();
            player.update();
        }
        if (gameState == pauseState) {

        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (gameState == titleState) {
            ui.draw(g2);
        } else {
            map1.draw(g2);
            //UI
            ui.draw(g2);
        }
        g2.dispose();
    }
}


