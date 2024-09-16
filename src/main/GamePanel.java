package main;
import UI.UI;
import entity.Player;
import map.Map1;
import javax.swing.*;
import java.awt.*;
import UI.TitleScreenUI;
import UI.GarageUI;


public class GamePanel extends JPanel implements Runnable {

    // CONFIGURAÇÕES DE TELA
    public final int D_W = 1280;
    public final int D_H = 720;
    // TECLADO
    KeyHandler keyH = new KeyHandler(this);
    //  FPS
    int FPS = 60;
    // UI
    public UI ui = new TitleScreenUI(this);
    Thread gameThread;
    // Player
    Player player = new Player(this,keyH);
    Map1 map1 = new Map1(this, keyH, player);
    //GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState =2;
    public final int garageState = 3;

    public GamePanel(){
        this.setPreferredSize( new Dimension (D_W, D_H));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);


    }
    public void starGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
        gameState = titleState;
    }
    @Override
    public void run() {
        double drawnInterval = (double) 1000000000 /FPS;
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

    public int check_class(UI userInterface){
        if(userInterface instanceof GarageUI){
            return garageState;
        }
        if(userInterface instanceof TitleScreenUI){
            return titleState;
        }
        return -1;
    }

    public void update(){
        alterarImgCar();
        if(gameState == playState){
            //player.setCarID(ui.getCarIndex());
            map1.update();
            player.update();
        }if(gameState == pauseState){
            ui.drawPause();
            //ui.DrawPauseScreen();
        }
        if(gameState == garageState){
            if(check_class(ui) != garageState){
                ui = new GarageUI(this);
            }
        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (gameState == titleState){
            ui.draw(g2);
        }else if(gameState == garageState){
            ui.draw(g2);
        }
        else if(gameState == playState){
            map1.draw(g2);
        }else if(gameState == pauseState){
            map1.draw(g2);
            ui.setG2(g2);
            ui.drawPause();


        }
        else{

            //UI
            ui.draw(g2);
        }
        g2.dispose();
    }
//    public void alterarImgCar(){
//        if (ui.getCarIndex() == 0){
//            player.setCarImage("/car1/car1.png");
//        }else if(ui.getCarIndex() == 1){
//            player.setCarImage("/car1/car2.png");
//        } else if (ui.getCarIndex() == 2) {
//            player.setCarImage("/car1/car3.png");
//
//        }
//    }w


    public void alterarImgCar(){
      switch (ui.getCarIndex()){
          case 0:
              player.setCarImage("/car1/car1.png");
              break;
          case 1:
              player.setCarImage("/car1/car2.png");
              break;
          case 2:
              player.setCarImage("/car1/car3.png");
              break;
      }
    }


}


